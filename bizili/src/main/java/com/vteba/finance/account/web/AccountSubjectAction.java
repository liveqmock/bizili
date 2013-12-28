package com.vteba.finance.account.web;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.vteba.common.constant.CommonConst;
import com.vteba.common.constant.FileConst;
import com.vteba.finance.account.model.Subject;
import com.vteba.finance.account.service.ISubjectService;
import com.vteba.persister.generic.Page;
import com.vteba.util.common.ExcelExportUtils;
import com.vteba.util.common.ExcelImportUtils;
import com.vteba.util.reflection.ReflectUtils;
import com.vteba.util.web.struts.StrutsUtils;
import com.vteba.web.action.BaseAction;

/**
 * 会计科目action
 * @author yinlei 
 * date 2012-6-24 下午11:05:28
 */
public class AccountSubjectAction extends BaseAction<Subject> {
	private static final long serialVersionUID = 8869320614370120027L;
	private Subject model = new Subject();
	private ISubjectService subjectServiceImpl;
	private List<Subject> subjectList = new ArrayList<Subject>();
	
	// File list 文件域对应的文件内容
	private File uploadFile;
	// String list 文件域对应的文件类型
	private String uploadFileContentType;
	// String list 文件域对应的文件名字
	private String uploadFileFileName;
	//上传文件保存路径
	private String savePath;
	
	@Inject
	public void setSubjectServiceImpl(ISubjectService subjectServiceImpl) {
		this.subjectServiceImpl = subjectServiceImpl;
	}

	@Override
	public Subject getModel() {
		return model;
	}

	@Override
	public String initial() throws Exception {
		Page<Subject> pages = new Page<Subject>();
		ReflectUtils.emptyToNull(model);
		//排序
		if (page.getAscDesc() == null || page.getOrderBy() == null) {
			page.setAscDesc("asc");
			page.setOrderBy("subjectCode");
		}
		subjectServiceImpl.test();
		pages = subjectServiceImpl.queryForPageByModel(page, model);
		listResult = pages.getResult();
		setAttributeToRequest(CommonConst.PAGE_NAME, pages);
		return SUCCESS;
	}
	
	/**
	 * 新增会计科目，初始化和保存
	 * @author yinlei
	 * date 2012-6-25 下午2:42:39
	 */
	public String input() throws Exception {
		if (isInit()) {
			setTokenValue();
			return SUCCESS;
		}
		if (isTokenValueOK()) {
			boolean ret = subjectServiceImpl.saveSubject(model);
			if (ret) {
				setAttributeToRequest("msg", "新增会计科目成功。");
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 新增科目时，选择科目的父科目
	 * @author yinlei
	 * date 2012-6-26 下午3:09:17
	 */
	public String list() throws Exception {
		mapResult = subjectServiceImpl.getSubjectTreeList();
		return LIST;
	}
	
	/**
	 * 导入excel中的会计科目
	 * @author yinlei
	 * date 2012-7-8 下午10:12:17
	 */
	public String importExcel() throws Exception {
		if (isInit()) {
			setTokenValue();
			return SUCCESS;
		}
		if (isTokenValueOK()) {
			FileInputStream fis = new FileInputStream(getUploadFile());
			//验证导入的标题是否符合要求
			boolean check = ExcelImportUtils.checkExcelTitle(fis, FileConst.SUB_IMP_TITLE);
			if (check) {
				FileInputStream is = new FileInputStream(getUploadFile());
				//将excel转换为object
				List<Subject> subjects = ExcelImportUtils.makeExcelToObject(is, Subject.class, FileConst.SUB_IMP_COLUMN);
				//下面subjects进行处理
				if (subjects.size() > 0) {
					subjectServiceImpl.saveEntityBatch(subjects, 100);
				}
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 导出Excel
	 * @author yinlei
	 * date 2012-6-25 下午1:36:49
	 */
	public String export() throws Exception {
		List<Object[]> dataList = new ArrayList<Object[]>();
		String[] title = {};
		ExcelExportUtils excel = new ExcelExportUtils(title);
		HSSFWorkbook book = excel.makeObjectToExcel(dataList, "sheet1");
		
		HttpServletResponse response = StrutsUtils.getResponse();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("content-disposition", "attachment;filename=\"" + "subject.xls" + "\"");
		
		book.write(response.getOutputStream());
		response.getOutputStream().flush();
		response.flushBuffer();
		response.getOutputStream().close();
		
		return null;
	}

	/*********seter and getter**********/
	public List<Subject> getSubjectList() {
		return subjectList;
	}

	public void setSubjectList(List<Subject> subjectList) {
		this.subjectList = subjectList;
	}

	public File getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}

	public String getUploadFileContentType() {
		return uploadFileContentType;
	}

	public void setUploadFileContentType(String uploadFileContentType) {
		this.uploadFileContentType = uploadFileContentType;
	}

	public String getUploadFileFileName() {
		return uploadFileFileName;
	}

	public void setUploadFileFileName(String uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}

	public String getSavePath() {
		return ServletActionContext.getServletContext().getRealPath(savePath);
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

}
