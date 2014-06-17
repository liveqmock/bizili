package com.vteba.tx.hibernate.transformer;

import java.util.Arrays;

import org.hibernate.transform.AliasedTupleSubsetResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vteba.lang.bytecode.ConstructorAccess;
import com.vteba.lang.bytecode.FieldAccess;
import com.vteba.tx.hibernate.QueryStatement;
import com.vteba.tx.hibernate.QueryStatementCache;
import com.vteba.utils.common.CamelCaseUtils;

/**
 * 基于别名的hibernate结果集转换器。基于JavaBean属性访问实现。且JavaBean的属性必须是protected的。
 * @author yinlei
 * @date 2013年11月13日 下午9:11:07
 */
public class FieldAliasedTransformer extends AliasedTupleSubsetResultTransformer {
	private static final Logger logger = LoggerFactory.getLogger(FieldAliasedTransformer.class);

	private static final long serialVersionUID = 1L;
	private final Class<?> resultClass;// 结果集类型
	private String[] columnAlias = {};// 查询语句栏位别名
	private ConstructorAccess<?> constructorAccess;// 构造函数访问器
	private FieldAccess fieldAccess;// 属性访问器
	private int[] fieldIndexs;// 方法索引
	private Class<?>[] fieldTypes;// 属性声明类型
	private boolean sqlQueryAll = false;// sql查询所有栏位，默认false
	private boolean hqlQueryAll = false;// hql查询所有栏位，默认false
	
	/**
	 * 构造基于sql/hql别名的结果集转换器
	 * @param resultClass
	 *            结果集类型
	 * @param sql
	 *            sql/hql语句
	 */
	public <T> FieldAliasedTransformer(Class<T> resultClass, String sql, boolean hql) {
		this.resultClass = resultClass;
		this.constructorAccess = ConstructorAccess.get(resultClass);
		this.fieldAccess = FieldAccess.get(resultClass);
		
		QueryStatement statement = QueryStatementCache.getInstance().get(sql);
		if (statement != null) {
			this.fieldIndexs = statement.getMethodIndexs();
			this.columnAlias = statement.getColumnAlias();
			this.fieldTypes = statement.getFieldTypes();
			this.hqlQueryAll = statement.isHqlQueryAll();
			this.sqlQueryAll = statement.isSqlQueryAll();
		} else {
			if (hql) {
				this.hqlQueryAll = ColumnAliasParser.get().isQueryAll(sql);
				if (hqlQueryAll) {
					QueryStatement stmt = new QueryStatement();
					stmt.setHqlQueryAll(hqlQueryAll);
					QueryStatementCache.getInstance().put(sql, stmt);
				}
			} else {
				if (sql.indexOf("*") > 0 && sql.indexOf("count(*)") < 0) {
					this.sqlQueryAll = true;
				}
			}

			if (!hqlQueryAll) {
				if (sqlQueryAll) {
					logger.info("使用了sql查询table所有栏位，将根据resultClass=[{}]的全部属性为查询栏位，可能导致异常。建议指定具体的别名。", resultClass.getName());
					String[] fieldNames = this.fieldAccess.getFieldNames();
					Class<?>[] fieldType = this.fieldAccess.getFieldTypes();
					
					int length = fieldNames.length;
					this.columnAlias = new String[length];
					this.fieldIndexs = new int[length];
					this.fieldTypes = new Class<?>[length];
					int j = 0;
					for (int i = 0; i < fieldNames.length; i++) {
						String fieldName = fieldNames[i];
						try {
							columnAlias[j] = CamelCaseUtils.toUnderScoreCase(fieldName);
							fieldIndexs[j] = fieldAccess.getIndex(fieldName);
							fieldTypes[j] = fieldType[i];
							j++;
						} catch (IllegalArgumentException e) {
							logger.info("方法[" + fieldName + "]不存在，可能是Entity关联栏位。" + e.getMessage());
						}
					}
					putSqlCache(sql);
				} else {// hql/sql基于别名的转换
					logger.info("执行{}查询，使用基于栏位别名的结果集转换。", (hql ? "hql" : "sql"));
					this.columnAlias = ColumnAliasParser.get().parseColumnAlias(sql, true);
					String[] fieldNames = this.fieldAccess.getFieldNames();
					Class<?>[] fieldType = this.fieldAccess.getFieldTypes();
					if (fieldType == null || fieldType.length == 0) {
						throw new IllegalArgumentException("resultClass=[" + resultClass.getName() + "]的属性访问修饰符不是protected，无法访问，请改为protected。");
					}
					this.fieldIndexs = new int[columnAlias.length];
					this.fieldTypes = new Class<?>[columnAlias.length];
					for (int i = 0; i < columnAlias.length; i++) {
						for (int j = 0; j < fieldNames.length; j++) {
							String fieldName = fieldNames[j];
							if (columnAlias[i].equals(fieldName)) {
								fieldIndexs[i] = this.fieldAccess.getIndex(fieldName);
								fieldTypes[i] = fieldType[j];
							}
						}
					}
					putSqlCache(sql);
				}
			}
		}

	}

	private void putSqlCache(String sql) {
		QueryStatement stmt = new QueryStatement();
		stmt.setFieldTypes(fieldTypes);
		stmt.setColumnAlias(columnAlias);
		stmt.setMethodIndexs(fieldIndexs);
		stmt.setHqlQueryAll(hqlQueryAll);
		stmt.setSqlQueryAll(sqlQueryAll);
		QueryStatementCache.getInstance().put(sql, stmt);
	}

	public boolean isTransformedValueATupleElement(String[] aliases, int tupleLength) {
		return false;
	}

	public Object transformTuple(Object[] tuple, String[] aliases) {
		if (hqlQueryAll) {
			return (tuple.length == 1 ? tuple[0] : tuple);
		}

		Object entity = constructorAccess.newInstance();
		for (int i = 0; i < aliases.length; i++) {
			fieldAccess.set(entity, fieldIndexs[i], tuple[i]);
		}
		return entity;
	}

	/**
	 * VO Bean中属性的类型，和sql栏位对应
	 * @return
	 */
	public Class<?>[] getFieldTypes() {
		return fieldTypes;
	}
	
	/**
	 * 获取sql中栏位别名数组
	 * 
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
		FieldAliasedTransformer that = (FieldAliasedTransformer) o;
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
		result = 31 * result
				+ (columnAlias != null ? Arrays.hashCode(columnAlias) : 0);
		return result;
	}
	
}
