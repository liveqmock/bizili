package com.vteba.persister.hibernate.transformer;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.transform.AliasedTupleSubsetResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vteba.lang.bytecode.ConstructorAccess;
import com.vteba.lang.bytecode.MethodAccess;
import com.vteba.persister.hibernate.QueryStatement;
import com.vteba.persister.hibernate.QueryStatementCache;
import com.vteba.util.common.CamelCaseUtils;
import com.vteba.util.reflection.AsmUtils;

/**
 * Hibernate SQL/HQL查询结果集，基于SQL/HQL别名进行JavaBean的转换。使用字节码处理。
 * @author yinlei
 * date 2013-06-08
 */
public class AliasedResultTransformer extends AliasedTupleSubsetResultTransformer{
	private static final Logger logger = LoggerFactory.getLogger(AliasedResultTransformer.class);
	
	private static final long serialVersionUID = 1L;
	private final Class<?> resultClass;//结果集类型
	private String[] columnAlias = {};//查询语句栏位别名
	private ConstructorAccess<?> constructorAccess;//构造函数访问器
	private MethodAccess methodAccess;//方法访问器
	private String[] methodNames;//方法名
	private int[] methodIndexs;//方法索引
	private Class<?>[][] argsTypes;//栏位别名参数类型/方法参数类型
	private String sql;//查询语句
	private boolean sqlQueryAll = false;//sql查询所有栏位，默认false
	private boolean hqlQueryAll = false;//hql查询所有栏位，默认false
	
	/**
	 * 构造基于sql/hql别名的结果集转换器
	 * @param resultClass 结果集类型
	 * @param sql sql/hql语句
	 */
	public <T> AliasedResultTransformer(Class<T> resultClass, String sql) {
		if (resultClass == null) {
			throw new IllegalArgumentException("resultClass cannot be null");
		}
		this.resultClass = resultClass;
		this.sql = sql;
		this.constructorAccess = AsmUtils.getInstance().createConstructorAccess(resultClass);
		this.methodAccess = AsmUtils.getInstance().createMethodAccess(resultClass);
		
		QueryStatement statement = QueryStatementCache.getInstance().get(sql);
		if (statement != null) {
			this.methodIndexs = statement.getMethodIndexs();
			this.columnAlias = statement.getColumnAlias();
			this.argsTypes = statement.getArgsTypes();
			this.hqlQueryAll = statement.isHqlQueryAll();
			this.sqlQueryAll = statement.isSqlQueryAll();
		} else {
			if (sql.indexOf("*") > 0) {
				this.sqlQueryAll = true;
			} else {
				this.hqlQueryAll = ColumnAliasParser.getInstance().isQueryAll(sql);
			}
			
			if (!hqlQueryAll && !sqlQueryAll) {//不是查询所有栏位，需要重新解析sql/hql
				this.columnAlias = ColumnAliasParser.getInstance().parseColumnAlias(sql, true);
				String[] tempMethodNames = this.methodAccess.getMethodNames();
				this.methodNames = new String[columnAlias.length];
				this.methodIndexs = new int[columnAlias.length];
				this.argsTypes = new Class<?>[columnAlias.length][];
				Class<?>[][] tempArgsTypes = this.methodAccess.getParameterTypes();
				for (int i = 0; i < columnAlias.length; i++) {
					for (int j = 0; j < tempMethodNames.length; j++) {
						String methodName = tempMethodNames[j];
						if (methodName.startsWith("set") 
								&& columnAlias[i].equals(StringUtils.uncapitalize(methodName.substring(3)))) {
							methodNames[i] = methodName;
							methodIndexs[i] = this.methodAccess.getIndex(methodName);
							argsTypes[i] = tempArgsTypes[j];
						}
					}
				}
				
				QueryStatement stmt = new QueryStatement();
				stmt.setArgsTypes(argsTypes);
				stmt.setColumnAlias(columnAlias);
				stmt.setMethodIndexs(methodIndexs);
				stmt.setHqlQueryAll(hqlQueryAll);
				stmt.setSqlQueryAll(sqlQueryAll);
				QueryStatementCache.getInstance().put(sql, stmt);
			}
		}
	}

	public boolean isTransformedValueATupleElement(String[] aliases, int tupleLength) {
		return false;
	}	

	public Object transformTuple(Object[] tuple, String[] aliases) {
		if (hqlQueryAll) {
			return tuple[0];
		} else if (sqlQueryAll) {
			this.columnAlias = aliases;
			//没有解析过，将解析
			if (methodIndexs == null || methodIndexs.length <= 0 || argsTypes == null || argsTypes.length <= 0) {
				this.methodIndexs = new int[columnAlias.length];
				this.argsTypes = new Class<?>[columnAlias.length][];
				Class<?>[][] tempArgsTypes = this.methodAccess.getParameterTypes();
				for (int i = 0; i < aliases.length; i++) {
					String methodName = "set" + CamelCaseUtils.toCapitalizeCamelCase(aliases[i]);
					try {
						methodIndexs[i] = methodAccess.getIndex(methodName);
						argsTypes[i] = tempArgsTypes[methodIndexs[i]];
					} catch (IllegalArgumentException e) {
						logger.info("方法[" + methodName + "]不存在，可能是Entity关联栏位。" + e.getMessage());
					}
				}
				QueryStatement stmt = new QueryStatement();
				stmt.setArgsTypes(argsTypes);
				stmt.setColumnAlias(columnAlias);
				stmt.setMethodIndexs(methodIndexs);
				stmt.setHqlQueryAll(hqlQueryAll);
				stmt.setSqlQueryAll(sqlQueryAll);
				QueryStatementCache.getInstance().put(sql, stmt);
			}
		}
		
		Object entity = constructorAccess.newInstance();
		for (int i = 0; i < aliases.length; i++) {
			methodAccess.invoke(entity, methodIndexs[i], tuple[i]);
		}
		return entity;
	}
	
	/**
	 * sql栏位别名对应的类型
	 * @return 别名类型
	 * @author yinlei
	 * date 2013-6-9 下午7:50:50
	 */
	public Class<?>[][] getArgsTypes() {
		return argsTypes;
	}

	/**
	 * 获取sql中栏位别名数组
	 * @return 栏位别名
	 * @author yinlei
	 * date 2013-6-9 下午7:50:04
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
		AliasedResultTransformer that = (AliasedResultTransformer) o;
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
