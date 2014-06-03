package com.vteba.tx.hibernate;

import java.io.Serializable;

/**
 * 查询语句解析结果
 * @author yinlei
 * date 2013-6-14 上午10:05:50
 */
public class QueryStatement implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String[] columnAlias;
	private int[] methodIndexs;
	private Class<?>[][] argsTypes;
	private Class<?>[] fieldTypes;
	private boolean hqlQueryAll;
	private boolean sqlQueryAll;
	
	/**
	 * @return the columnAlias
	 */
	public String[] getColumnAlias() {
		return columnAlias;
	}

	/**
	 * @param columnAlias the columnAlias to set
	 */
	public void setColumnAlias(String[] columnAlias) {
		this.columnAlias = columnAlias;
	}

	/**
	 * @return the methodIndexs
	 */
	public int[] getMethodIndexs() {
		return methodIndexs;
	}

	/**
	 * @param methodIndexs the methodIndexs to set
	 */
	public void setMethodIndexs(int[] methodIndexs) {
		this.methodIndexs = methodIndexs;
	}

	/**
	 * @return the argsTypes
	 */
	public Class<?>[][] getArgsTypes() {
		return argsTypes;
	}

	/**
	 * @param argsTypes the argsTypes to set
	 */
	public void setArgsTypes(Class<?>[][] argsTypes) {
		this.argsTypes = argsTypes;
	}
	
	/**
	 * @return the hqlQueryAll
	 */
	public boolean isHqlQueryAll() {
		return hqlQueryAll;
	}

	/**
	 * @param hqlQueryAll the hqlQueryAll to set
	 */
	public void setHqlQueryAll(boolean hqlQueryAll) {
		this.hqlQueryAll = hqlQueryAll;
	}

	/**
	 * @return the sqlQueryAll
	 */
	public boolean isSqlQueryAll() {
		return sqlQueryAll;
	}

	/**
	 * @param sqlQueryAll the sqlQueryAll to set
	 */
	public void setSqlQueryAll(boolean sqlQueryAll) {
		this.sqlQueryAll = sqlQueryAll;
	}

	public Class<?>[] getFieldTypes() {
		return fieldTypes;
	}

	public void setFieldTypes(Class<?>[] fieldTypes) {
		this.fieldTypes = fieldTypes;
	}
}
