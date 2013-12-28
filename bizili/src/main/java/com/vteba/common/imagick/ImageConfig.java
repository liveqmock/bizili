package com.vteba.common.imagick;

/**
 * 配置图片信息
 */
public class ImageConfig {

	//是否原始图片
	private Boolean isOrigin;
	
	//是否裁剪
	private Boolean hasCrop;
	
	//目标图片高
	private Integer destHeight;
	
	//目标图片宽
	private Integer destWidth;
	
	//目标图片保存路径
	private String savePath;

	//是否压水印
	private Boolean isMark = false;;
	
	//水印位置
	private String markGravity = "SouthEast";
	
	//水印透明度
	private String markDissolve = "20";
	
	//水印文件路径
	private String markFilePath;

	public Boolean getIsOrigin() {
		return isOrigin;
	}

	public void setIsOrigin(Boolean isOrigin) {
		this.isOrigin = isOrigin;
	}

	public Integer getDestHeight() {
		return destHeight;
	}

	public void setDestHeight(Integer destHeight) {
		this.destHeight = destHeight;
	}

	public Integer getDestWidth() {
		return destWidth;
	}

	public void setDestWidth(Integer destWidth) {
		this.destWidth = destWidth;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public Boolean getIsMark() {
		return isMark;
	}

	public void setIsMark(Boolean isMark) {
		this.isMark = isMark;
	}

	public String getMarkGravity() {
		return markGravity;
	}

	public void setMarkGravity(String markGravity) {
		this.markGravity = markGravity;
	}

	public String getMarkDissolve() {
		return markDissolve;
	}

	public void setMarkDissolve(String markDissolve) {
		this.markDissolve = markDissolve;
	}

	public String getMarkFilePath() {
		return markFilePath;
	}

	public void setMarkFilePath(String markFilePath) {
		this.markFilePath = markFilePath;
	}

	public Boolean getHasCrop() {
		return hasCrop;
	}

	public void setHasCrop(Boolean hasCrop) {
		this.hasCrop = hasCrop;
	}
	
}
