package com.vteba.common.web;

import com.vteba.user.model.EmpUser;
import com.vteba.web.action.BaseAction;

/**
 * session 超时 action
 * @author yinlei
 * date 2012-9-7 下午8:07:47
 */
public class SessionTimeoutAction extends BaseAction<EmpUser> {

	private static final long serialVersionUID = 813147558177623838L;
	
	@Override
	public EmpUser getModel() {
		return null;
	}

	@Override
	public String initial() throws Exception {
		return SUCCESS;
	}
	
}
