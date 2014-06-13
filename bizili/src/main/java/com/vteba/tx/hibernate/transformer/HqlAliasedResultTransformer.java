package com.vteba.tx.hibernate.transformer;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.transform.AliasedTupleSubsetResultTransformer;

import com.vteba.lang.bytecode.ConstructorAccess;
import com.vteba.lang.bytecode.MethodAccess;
import com.vteba.tx.hibernate.QueryStatement;
import com.vteba.tx.hibernate.QueryStatementCache;
import com.vteba.util.reflection.AsmUtils;

/**
 * Hibernate HQL查询结果集转换器，基于HQL别名进行JavaBean的转换。使用asm处理。<br>
 * 基于JavaBean的getter和setter实现。
 * @author yinlei
 * date 2013-06-08
 */
public class HqlAliasedResultTransformer extends AliasedTupleSubsetResultTransformer {

	private static final long serialVersionUID = 1L;
	private final Class<?> resultClass;// 结果集类型
	private String[] columnAlias = {};// 查询语句栏位别名
	private ConstructorAccess<?> constructorAccess;// 构造函数访问器
	private MethodAccess methodAccess;// 方法访问器
	private int[] methodIndexs;// 方法索引
	
	/**
	 * 构造基于hql语句中栏位别名的结果集转换器
	 * @param resultClass 结果集类型
	 * @param hql hql语句
	 */
	public <T> HqlAliasedResultTransformer(Class<T> resultClass, String hql) {
		this.resultClass = resultClass;
		this.constructorAccess = AsmUtils.get().createConstructorAccess(resultClass);
		this.methodAccess = AsmUtils.get().createMethodAccess(resultClass);
		QueryStatement statement = QueryStatementCache.getInstance().get(hql);
		if (statement != null) {
			this.methodIndexs = statement.getMethodIndexs();
			this.columnAlias = statement.getColumnAlias();
		} else {
			this.columnAlias = ColumnAliasParser.get().parseColumnAlias(hql, true);
			String[] tempMethodNames = this.methodAccess.getMethodNames();
			this.methodIndexs = new int[columnAlias.length];
			for (int i = 0; i < columnAlias.length; i++) {
				for (int j = 0; j < tempMethodNames.length; j++) {
					String methodName = tempMethodNames[j];
					if (methodName.startsWith("set") && columnAlias[i].equals(StringUtils.uncapitalize(methodName.substring(3)))) {
						methodIndexs[i] = this.methodAccess.getIndex(methodName);
					}
				}
			}
			putSqlCache(hql);
		}

	}

	private void putSqlCache(String sql) {
		QueryStatement stmt = new QueryStatement();
		stmt.setColumnAlias(columnAlias);
		stmt.setMethodIndexs(methodIndexs);
		QueryStatementCache.getInstance().put(sql, stmt);
	}

	public boolean isTransformedValueATupleElement(String[] aliases, int tupleLength) {
		return false;
	}

	public Object transformTuple(Object[] tuple, String[] aliases) {
		Object entity = constructorAccess.newInstance();
		for (int i = 0; i < aliases.length; i++) {
			methodAccess.invoke(entity, methodIndexs[i], tuple[i]);
		}
		return entity;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		HqlAliasedResultTransformer that = (HqlAliasedResultTransformer) o;
		if (!resultClass.equals(that.resultClass)) {
			return false;
		}
		if (!Arrays.equals(columnAlias, that.columnAlias)) {
			return false;
		}
		return true;
	}

	public int hashCode() {
		int result = resultClass.hashCode();
		result = 31 * result + (columnAlias != null ? Arrays.hashCode(columnAlias) : 0);
		return result;
	}
}
