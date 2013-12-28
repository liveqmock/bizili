package com.vteba.common.web;

import java.io.ByteArrayInputStream;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.vteba.util.common.RandomNumber;

/**
 * 随机验证码action 
 * @author yinlei 
 * @date 2012-6-3 下午3:29:48
 */
public class RandomNumberAction extends ActionSupport {

	private static final long serialVersionUID = 9209061950889403907L;
	private ByteArrayInputStream inputStream;

	public String execute() throws Exception {
		RandomNumber randomNum = RandomNumber.Instance();
		setInputStream(randomNum.getImage());// 取得带有随机字符串的图片
		String authCode = randomNum.getString().toString().toLowerCase();
		ActionContext.getContext().getSession().put("random", authCode);// 取得随机字符串放入HttpSession
		return SUCCESS;
	}

	public void setInputStream(ByteArrayInputStream inputStream) {
		this.inputStream = inputStream;
	}

	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}

}
