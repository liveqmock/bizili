package com.vteba.finance.account.remote;

import javax.inject.Inject;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.spring.SpringCreator;

import com.vteba.finance.account.model.Subject;
import com.vteba.finance.account.service.ISubjectService;

/**
 * 会计科目 dwr Bean
 * @author yinlei 
 * date 2012-7-5 下午4:39:05
 */
@RemoteProxy(creator = SpringCreator.class)
public class SubjectBean {
	private ISubjectService subjectServiceImpl;
	
	@Inject
	public void setSubjectServiceImpl(ISubjectService subjectServiceImpl) {
		this.subjectServiceImpl = subjectServiceImpl;
	}


	/**
	 * 验证会计科目是否存在
	 * @return 科目名字
	 * @author yinlei
	 * date 2012-7-5 下午4:41:47
	 */
	@RemoteMethod
	public String checkSubjectExist(String subjectId){
		String hql = " select s from Subject s where s.subjectCode = ?1 ";
		Subject bean = subjectServiceImpl.uniqueResultByHql(hql, subjectId);
		String result = "error";
		if (bean != null) {
			result = bean.getSubjectName();
		}
		return result;
	}
}
