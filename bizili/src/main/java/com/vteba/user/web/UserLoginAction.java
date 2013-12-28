package com.vteba.user.web;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.vteba.user.model.EmpUser;
import com.vteba.user.service.IEmpUserService;
import com.vteba.web.action.BaseAction;

/**
 * 用户登录跳转 
 * @author yinlei 
 * date 2012-6-8 下午11:13:40
 */
public class UserLoginAction extends BaseAction<EmpUser> {
	private static final long serialVersionUID = -5663942410979632494L;
	private boolean expired;//超过并发session数，session失效
	private boolean authError;//验证失败
	
	// File list封装多个文件域对应的文件内容
	private List<File> uploadFile = new ArrayList<File>();
	// String list 封装多个文件域对应的文件类型
	private List<String> uploadFileContentType = new ArrayList<String>();
	// String list封装多个文件域对应的文件名字
	private List<String> uploadFileFileName = new ArrayList<String>();
	// 上传路径
	private String savePath;

	private EmpUser model = new EmpUser();
	private IEmpUserService empUserServiceImpl;
	
	public IEmpUserService getEmpUserServiceImpl() {
		return empUserServiceImpl;
	}
	//@Inject
	//private ShaPasswordEncoder shaPasswordEncoder;
	@Inject
	public void setEmpUserServiceImpl(IEmpUserService empUserServiceImpl) {
		this.empUserServiceImpl = empUserServiceImpl;
	}

	@Override
	public EmpUser getModel() {
		return model;
	}

	@Override
	public String initial() throws Exception {
		/*long l0 = System.currentTimeMillis();
		Authentication authentication = SecurityContextHolderUtils.getAuthentication();
		if (authentication != null) {
			if (authentication.getPrincipal().toString().equals("anonymousUser")) {
				Object object = StrutsUtils.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
				if (object != null) {
					ActionContext ctx = ActionContext.getContext();
					Object obj = ctx.get(WebAttributes.AUTHENTICATION_EXCEPTION);
					System.out.println(obj);
					//saveActionMessage("msg", "您输入的用户名或密码错误。");
				}
			}
		}
		long l1 = System.currentTimeMillis();
		System.out.println(l1-l0);
		Object object = StrutsUtils.getSession().getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		if (object != null) {
			//saveActionMessage("msg", "您输入的用户名或密码错误。");
		}
		long l2 = System.currentTimeMillis();
		System.out.println(l2-l1);*/
		//String aa = shaPasswordEncoder.encodePassword("123456", "yinlei_nb");
		//System.out.println(aa);
		if (isInit()) {
			return SUCCESS;
		}else if (isAuthError()) {
			setAttributeToRequest("msg", "您输入的用户名或密码错误。");
		} else if (isExpired()) {
			setAttributeToRequest("msg", "该用户已经登录了。");
		}
		return SUCCESS;
	}
	
	public String importExcel(){
		return SUCCESS;
	}
	
	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public boolean isAuthError() {
		return authError;
	}

	public void setAuthError(boolean authError) {
		this.authError = authError;
	}

	public List<File> getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(List<File> uploadFile) {
		this.uploadFile = uploadFile;
	}

	public List<String> getUploadFileContentType() {
		return uploadFileContentType;
	}

	public void setUploadFileContentType(List<String> uploadFileContentType) {
		this.uploadFileContentType = uploadFileContentType;
	}

	public List<String> getUploadFileFileName() {
		return uploadFileFileName;
	}

	public void setUploadFileFileName(List<String> uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

}
