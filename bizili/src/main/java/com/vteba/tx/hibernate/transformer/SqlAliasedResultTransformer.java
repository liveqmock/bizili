package com.vteba.tx.hibernate.transformer;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.transform.AliasedTupleSubsetResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vteba.lang.bytecode.ConstructorAccess;
import com.vteba.lang.bytecode.MethodAccess;
import com.vteba.tx.hibernate.QueryStatement;
import com.vteba.tx.hibernate.QueryStatementCache;
import com.vteba.utils.common.CamelCaseUtils;
import com.vteba.utils.reflection.AsmUtils;

/**
 * Hibernate SQL查询结果集转换器，基于SQL别名进行JavaBean的转换。使用asm处理。<br>
 * 基于JavaBean的getter和setter实现。
 * @author yinlei
 * date 2013-06-08
 */
public class SqlAliasedResultTransformer extends AliasedTupleSubsetResultTransformer {
	private static final Logger logger = LoggerFactory.getLogger(SqlAliasedResultTransformer.class);

	private static final long serialVersionUID = 1L;
	private final Class<?> resultClass;// 结果集类型
	private String[] columnAlias = {};// 查询语句栏位别名
	private ConstructorAccess<?> constructorAccess;// 构造函数访问器
	private MethodAccess methodAccess;// 方法访问器
	private int[] methodIndexs;// 方法索引
	private Class<?>[][] argsTypes;// 栏位别名参数类型/方法参数类型
	private boolean sqlQueryAll = false;// sql查询所有栏位，默认false
	
	/**
	 * 构造基于sql别名的结果集转换器
	 * @param resultClass 结果集类型
	 * @param sql sql语句
	 */
	public <T> SqlAliasedResultTransformer(Class<T> resultClass, String sql) {
		this.resultClass = resultClass;
		this.constructorAccess = AsmUtils.get().createConstructorAccess(resultClass);
		this.methodAccess = AsmUtils.get().createMethodAccess(resultClass);
		QueryStatement statement = QueryStatementCache.getInstance().get(sql);
		if (statement != null) {
			this.methodIndexs = statement.getMethodIndexs();
			this.columnAlias = statement.getColumnAlias();
			this.argsTypes = statement.getArgsTypes();
			this.sqlQueryAll = statement.isSqlQueryAll();
		} else {
			if (sql.indexOf("*") > 0 && sql.indexOf("count(*)") < 0) {
				this.sqlQueryAll = true;
			}

			if (sqlQueryAll) {// 如果是查询所有栏位，则基于resultClass进行转换，尝试resultClass所有的属性。
				String[] tempMethodNames = this.methodAccess.getMethodNames();
				Class<?>[][] tempArgsTypes = this.methodAccess.getParameterTypes();
				int length = tempMethodNames.length / 2;
				this.columnAlias = new String[length];
				this.methodIndexs = new int[length];
				this.argsTypes = new Class<?>[length][];
				int j = 0;
				for (int i = 0; i < tempMethodNames.length; i++) {
					String methodName = tempMethodNames[i];
					if (methodName.startsWith("set")) {
						if (tempArgsTypes[i][0].isInterface()) {//接口类直接滤过
							continue;
						}
						try {
							columnAlias[j] = CamelCaseUtils.toUnderScoreCase(StringUtils.uncapitalize(methodName.substring(3)));
							methodIndexs[j] = methodAccess.getIndex(methodName);
							argsTypes[j] = tempArgsTypes[i];
							j++;
						} catch (IllegalArgumentException e) {
							logger.info("方法[" + methodName + "]不存在，可能是Entity关联栏位。" + e.getMessage());
						}
					}
				}
				putSqlCache(sql);
			} else {// sql查询具体栏位，基于别名的转换
				this.columnAlias = ColumnAliasParser.get().parseColumnAlias(sql, true);
				String[] tempMethodNames = this.methodAccess.getMethodNames();
				this.methodIndexs = new int[columnAlias.length];
				this.argsTypes = new Class<?>[columnAlias.length][];
				Class<?>[][] tempArgsTypes = this.methodAccess.getParameterTypes();
				for (int i = 0; i < columnAlias.length; i++) {
					for (int j = 0; j < tempMethodNames.length; j++) {
						String methodName = tempMethodNames[j];
						if (methodName.startsWith("set") && columnAlias[i].equals(StringUtils.uncapitalize(methodName.substring(3)))) {
							methodIndexs[i] = this.methodAccess.getIndex(methodName);
							argsTypes[i] = tempArgsTypes[j];
						}
					}
				}
				putSqlCache(sql);
			}
		}

	}

	private void putSqlCache(String sql) {
		QueryStatement stmt = new QueryStatement();
		stmt.setArgsTypes(argsTypes);
		stmt.setColumnAlias(columnAlias);
		stmt.setMethodIndexs(methodIndexs);
		stmt.setSqlQueryAll(sqlQueryAll);
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

	/**
	 * sql栏位别名对应的类型
	 * 
	 * @return 别名类型
	 * @author yinlei date 2013-6-9 下午7:50:50
	 */
	public Class<?>[][] getArgsTypes() {
		return argsTypes;
	}

	/**
	 * 获取sql中栏位别名数组
	 * @return 栏位别名
	 * @author yinlei date 2013-6-9 下午7:50:04
	 */
	public String[] getColumnAlias() {
		return columnAlias;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		SqlAliasedResultTransformer that = (SqlAliasedResultTransformer) o;
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
