package com.vteba.common.imagick;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.vteba.common.exception.BaseException;
import com.vteba.util.common.SystemUtil;

/**
 * 图片处理管理
 * 按照系统图片定义要求，来生成处理后的图片
 */
public class ImageManager {

	/**********************************
	 ********* 从配置文件读取 ***********
	 *********************************/
	// CONVERT命令行
	private String convertCmd;
	
	//COMPOSITE命令行
	private String compositeCmd;
	
	//图片配置列表
	private List<ImageConfig> imageConfigList;
	
	/**********************************
	 ********* 从外部调用设定 ***********
	 *********************************/
	//上传原始图片IS
	private InputStream originFileInputStream;
	//原始图片名称
	private String originFileName;
	
	//自定义图片名称(包括扩张名);主要硬盘上以存在的图片
	private String customFileName;
	
	/**
	 * 图片保存跟目录
	 */
	private String rootPath;
	/**
	 * 原始图片保存路径
	 */
	private String originFilePath;

	/**
	 * 执行图片处理
	 * @return	处理结果
	 * true:处理成功
	 * false:处理失败
	 */
	public List<String> execute(){
		
		//判断原始图片IS和原始图片名称是否为空
		if(originFileInputStream == null || StringUtils.isEmpty(originFileName)){
			return null;
		}
		
		List<String> filePathList = new ArrayList<String>();
		
		//取得图片根目录
		rootPath = SystemUtil.getAppConfig("file.image.path");
		
		String prefix;
		
		//主动传入文件名(包括扩张名)
		if(StringUtils.isNotBlank(customFileName)){
			prefix = customFileName;
		}else{
			//取得文件创建时间前缀
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmssSSS");
			prefix = df.format(new Date())+UUID.randomUUID().toString().substring(0,4);
		}
//		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddhhmmssSSS");
//		prefix = df.format(new Date())+UUID.randomUUID().toString().substring(0,4);

		//根据图片配置要求循环处理图片
		for(ImageConfig config : imageConfigList){
//			
			//处理图片
			String path = convertImageToDest(config, prefix);
			if(StringUtils.isEmpty(path)){
				return null;
			}
			filePathList.add(path.substring(rootPath.length()));

		}
		
		return filePathList;
		
	}
	
	/**
	 * 根据图片配置要求处理图片
	 * 
	 * 图片裁剪逻辑：
	 * 当原始图片的宽和高皆<=目标图片的宽和高
	 * 		不做任何操作，直接将图片保存
	 * 当原始图片的宽<=目标图片的宽	
	 * 		原始图片左右不动，上下裁剪为目标图片的高
	 * 当原始图片的高<=目标图片的高
	 * 		原始图片上下不动，左右裁剪为目标图片的宽，
	 * 当原始图片的宽和高皆>目标图片的宽和高
	 * 		当原始图片宽高比>目标图片宽高比
	 * 			将原始图片按照目标图片的高缩小，然后左右裁剪至目标图片的宽
	 * 		当原始图片宽高比<目标图片宽高比
	 * 			将原始图片按照目标图片的宽缩小，然后上下裁剪至目标图片的高
	 * 
	 * 图片缩小逻辑
	 * 当原始图片的宽和高皆<=目标图片的宽和高
	 * 		不做任何操作，直接将图片保存
	 * 当原始图片的宽和高皆>目标图片的宽和高
	 * 		当原始图片宽高比>目标图片宽高比
	 * 			将原始图片按照目标图片的宽缩小
	 * 		当原始图片宽高比<目标图片宽高比
	 * 			将原始图片按照目标图片的高缩小
	 *  
	 * @param config	图片配置要求
	 * @return			已处理图片保存路径
	 */
	private String convertImageToDest(ImageConfig config, String prefix){
		
		String filePath = null;
		
		//Magick处理实体
		MagickConvertImpl magick = new MagickConvertImpl();
		
		try{
			
			String dir = String.format("%s%s", rootPath, config.getSavePath());
			SystemUtil.createDirectory(new File(dir));
			
			/*Lik 鉴于sina头像没有扩展名 修改start*/
			String fileName = originFileName;
			//构建图片名
			int extentdedPos = fileName.lastIndexOf(".");
			if(extentdedPos != -1 && prefix.indexOf(".") == -1){
				fileName = String.format("%s%s", prefix,originFileName.substring(extentdedPos));
			}
			/*Lik 鉴于sina头像没有扩展名 修改end*/
			filePath = String.format("%s%s", dir, fileName);
			
			//原始图片处理
			if(config.getIsOrigin()){
				
				saveFile(originFileInputStream, filePath);
				originFilePath = filePath;
			
			//非原始图片处理
			}else{
				
				//操作命令
				StringBuffer cmdText = new StringBuffer();
				BufferedImage resImage = ImageIO.read(new File(originFilePath));
				//原始图片宽度
				int resImageWidth = resImage.getWidth();
				//原始图片高度
				int resImageHeight = resImage.getHeight();
				//目标图片宽度
				int destImageWidth = config.getDestWidth();
				//目标图片高度
				int destImageHeight = config.getDestHeight();
				//原始图片宽高比
				double resImageRatio = (double)resImageWidth/(double)resImageHeight;
				//目标图片宽高比
				double destImageRatio = (double)destImageWidth/(double)destImageHeight;
				
				//Magick设置
				magick.setCommand(convertCmd);
				magick.arg(originFilePath);
				magick.arg(filePath);
				
				//裁剪操作
				if(config.getHasCrop()){
					
					//原始图片的高宽都小于等于目标图片
					if(resImageWidth <= destImageWidth
					&& resImageHeight <= destImageHeight){
						
						FileUtils.copyFile(new File(originFilePath), new File(filePath));
						return filePath;//add by lim 2011-9-15
					
					//原始图片的宽小于等于目标图片的宽
					}else if(resImageWidth <= destImageWidth){
						
						cmdText.setLength(0);
						cmdText.append(resImageWidth);
						cmdText.append("x");
						cmdText.append(destImageHeight);
						cmdText.append("+0+");
						cmdText.append((resImageHeight-destImageHeight)/2);
						cmdText.append(" -page +0+0");
						magick.crop(cmdText.toString());
					
					//原始图片的高小于等于目标图片的高
					}else if(resImageHeight <= destImageHeight){
						cmdText.setLength(0);
						cmdText.append(destImageWidth);
						cmdText.append("x");
						cmdText.append(resImageHeight);
						cmdText.append("+");
						cmdText.append((resImageWidth-destImageWidth)/2);
						cmdText.append("+0");
						cmdText.append(" -page +0+0");
						magick.crop(cmdText.toString());
					
					//原始图片的宽高皆大于目标图片的宽高
					}else{
						
						//原始图片宽高比>目标图片宽高比
						if(resImageRatio > destImageRatio){
							
							magick.scaleHeight(destImageHeight);
							
							cmdText.setLength(0);
							cmdText.append(destImageWidth);
							cmdText.append("x");
							cmdText.append(destImageHeight);
							cmdText.append("+");
							cmdText.append((resImageWidth*destImageHeight/resImageHeight-destImageWidth)/2);
							cmdText.append("+0");
							cmdText.append(" -page +0+0");
							magick.crop(cmdText.toString());
						
						//原始图片宽高比<目标图片宽高比
						}else if(resImageRatio < destImageRatio){
							
							magick.scaleWidth(destImageWidth);
							
							cmdText.setLength(0);
							cmdText.append(destImageWidth);
							cmdText.append("x");
							cmdText.append(destImageHeight);
							cmdText.append("+0+");
							cmdText.append((resImageHeight*destImageWidth/resImageWidth-destImageHeight)/2);
							cmdText.append(" -page +0+0");
							magick.crop(cmdText.toString());
						
						//原始图片宽高比==目标图片宽高比
						}else{
							
							magick.scaleHeight(destImageHeight);
						}
					}
					
				//缩小操作
				}else{
					
					//原始图片的高宽都小于等于目标图片
					if((resImageWidth <= destImageWidth || destImageWidth==0)
					&& (resImageHeight <= destImageHeight || destImageHeight==0)){
						
						FileUtils.copyFile(new File(originFilePath), new File(filePath));
						return filePath;//add by lim 2011-9-15
					//目标图片不限宽
					}else if(destImageWidth == 0){
						
						magick.scaleHeight(destImageHeight);
					
					//目标图片不限高
					}else if(destImageHeight == 0){
						magick.scaleWidth(destImageWidth);
					
					//原始图片宽高比>目标图片宽高比
					}else if(resImageRatio > destImageRatio){
						
						magick.scaleWidth(destImageWidth);
					
					//原始图片宽高比<目标图片宽高比
					}else if(resImageRatio < destImageRatio){
						
						magick.scaleHeight(destImageHeight);
					
					//原始图片宽高比==目标图片宽高比
					}else{
						magick.scaleHeight(destImageHeight);
					}
					
				}
				
			}
			
			//是否加水印
			if(config.getIsMark()){
				String markFilePath = config.getMarkFilePath();
				if(StringUtils.isEmpty(markFilePath)){
				//	markFilePath = ServletActionContext.getServletContext().getRealPath("img/press.jpg");
				}
				
				magick.setCommand(compositeCmd);
				magick.arg(config.getMarkFilePath());
				magick.arg(filePath);
				magick.arg(filePath);
				magick.dissolve(config.getMarkDissolve());
				magick.gravity(config.getMarkGravity());
			
			}
			
			if(!StringUtils.isEmpty(magick.command)){
				boolean isSuccess = magick.execute();
				if(!isSuccess){
					return null;
				}
			}
			
		}catch(Exception e){
			
			e.printStackTrace();
			return null;
			
		}
		
		return filePath;
	}
	
	
	/**
	 * 保存文件
	 * @param file		文件
	 * @param filePath	文件路径
	 * @throws IOException
	 */
	private void saveFile(InputStream file, String filePath){
		FileOutputStream fos = null;
		FileInputStream fis = null;
		DataInputStream dis = null;
		try {
			fos = new FileOutputStream(filePath);
			if(file instanceof ByteArrayInputStream){
				dis = new DataInputStream(file);
				byte[] buffer = new byte[102400];
				int len = 0;
				while((len = dis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
			}else{
				fis = (FileInputStream)file;
				byte[] buffer = new byte[102400];
				int len = 0;
				while((len = fis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
			}
			
			
		}catch(IOException e){
			throw  new BaseException("[ERROR]文件保存时出错。",e);
			
		} finally {
			try{
				
				if(fos != null) {
					fos.close();
				}
				if(fis != null) {
					fis.close();
				}
				if(dis!=null){
					dis.close();
				}
			}catch(IOException e){
				throw  new BaseException("[ERROR]文件保存时出错。",e);
			}
		}
	}

	public String getConvertCmd() {
		return convertCmd;
	}

	public void setConvertCmd(String convertCmd) {
		this.convertCmd = convertCmd;
	}

	public String getCompositeCmd() {
		return compositeCmd;
	}

	public void setCompositeCmd(String compositeCmd) {
		this.compositeCmd = compositeCmd;
	}

	public List<ImageConfig> getImageConfigList() {
		return imageConfigList;
	}

	public void setImageConfigList(List<ImageConfig> imageConfigList) {
		this.imageConfigList = imageConfigList;
	}

	public String getRootPath() {
		return rootPath;
	}

	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	public String getOriginFilePath() {
		return originFilePath;
	}

	public void setOriginFilePath(String originFilePath) {
		this.originFilePath = originFilePath;
	}

	public InputStream getOriginFileInputStream() {
		return originFileInputStream;
	}

	public void setOriginFileInputStream(InputStream originFileInputStream) {
		this.originFileInputStream = originFileInputStream;
	}

	public String getOriginFileName() {
		return originFileName;
	}

	public void setOriginFileName(String originFileName) {
		this.originFileName = originFileName;
	}

	public String getCustomFileName() {
		return customFileName;
	}

	public void setCustomFileName(String customFileName) {
		this.customFileName = customFileName;
	}
	
}
