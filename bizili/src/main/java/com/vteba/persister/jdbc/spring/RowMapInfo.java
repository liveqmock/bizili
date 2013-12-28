package com.vteba.persister.jdbc.spring;

import java.io.Serializable;

/**
 * Spring RowMapper 栏位映射信息。
 * @author yinlei
 * date 2013-6-25 上午10:05:50
 */
public class RowMapInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int columnCount = 0;
	private String[] columnLabels;
	private String[] methodNames;
	
	/**
	 * @return the columnCount
	 */
	public int getColumnCount() {
		return columnCount;
	}

	/**
	 * @param columnCount
	 *            the columnCount to set
	 */
	public void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
	}

	/**
	 * @return the columnLabels
	 */
	public String[] getColumnLabels() {
		return columnLabels;
	}

	/**
	 * @param columnLabels
	 *            the columnLabels to set
	 */
	public void setColumnLabels(String[] columnLabels) {
		this.columnLabels = columnLabels;
	}

	/**
	 * @return the methodNames
	 */
	public String[] getMethodNames() {
		return methodNames;
	}

	/**
	 * @param methodNames
	 *            the methodNames to set
	 */
	public void setMethodNames(String[] methodNames) {
		this.methodNames = methodNames;
	}
	
}
