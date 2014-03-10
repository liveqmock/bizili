package com.vteba.web.action;

import com.vteba.persister.generic.Page;

/**
 * 对分页数据的封装。
 * @param <T> 分页封装的实体对象
 * @author yinlei
 * 2014-3-10 下午1:58:45
 */
public class FormBean<T> {
	private Page<T> page = new Page<T>();

	public Page<T> getPage() {
		return page;
	}

	public void setPage(Page<T> page) {
		this.page = page;
	}
	
}
