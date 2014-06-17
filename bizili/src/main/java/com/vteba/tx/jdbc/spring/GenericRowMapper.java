package com.vteba.tx.jdbc.spring;

import java.sql.Blob;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.vteba.lang.bytecode.ConstructorAccess;
import com.vteba.lang.bytecode.MethodAccess;
import com.vteba.utils.common.CamelCaseUtils;
import com.vteba.utils.reflection.AsmUtils;

/**
 * Spring JdbcTemplate 通用RowMapper，基于getter，setter实现。
 * @author yinlei
 * date 2013-6-25 下午10:20:06
 * @param <T> RowMapper要转换的对象
 */
public class GenericRowMapper<T> implements RowMapper<T> {
	private Class<T> resultClass;
	private ConstructorAccess<T> constructorAccess;
	private MethodAccess methodAccess;
	private int columnCount = 0;
	private String[] columnLabels;
	private String[] methodNames;
	private String sql;
	
	public GenericRowMapper(Class<T> resultClass, String sql) {
		this.resultClass = resultClass;
		this.sql = sql;
		constructorAccess = AsmUtils.get().createConstructorAccess(this.resultClass);
		methodAccess = AsmUtils.get().createMethodAccess(this.resultClass);
		RowMapInfo rowMapInfo = RowMapInfoCache.getInstance().get(this.sql);
		if (rowMapInfo != null) {
			columnCount = rowMapInfo.getColumnCount();
			columnLabels = rowMapInfo.getColumnLabels();
			methodNames = rowMapInfo.getMethodNames();
		}
	}
	
	@Override
	public T mapRow(ResultSet rs, int rowNum) throws SQLException {
		T entity = constructorAccess.newInstance();
		
		if (columnCount == 0) {
			ResultSetMetaData metaData = rs.getMetaData();
			columnCount = metaData.getColumnCount();
			columnLabels = new String[columnCount];
			methodNames = new String[columnCount];
			for (int c = 0; c < columnCount; c++) {
				String columnLabel = metaData.getColumnLabel(c + 1).toLowerCase();
				columnLabels[c] = columnLabel;
				
				String methodName = "set" + CamelCaseUtils.toCapitalizeCamelCase(columnLabel);
				methodNames[c] = methodName;
				methodAccess.invoke(entity, methodName, getResultSetValue(rs, c + 1));
			}
			RowMapInfo rowMapInfo = new RowMapInfo();
			rowMapInfo.setColumnCount(columnCount);
			rowMapInfo.setColumnLabels(columnLabels);
			rowMapInfo.setMethodNames(methodNames);
			RowMapInfoCache.getInstance().put(this.sql, rowMapInfo);
		} else {
			for (int c = 0; c < columnCount; c++) {
				methodAccess.invoke(entity, methodNames[c], getResultSetValue(rs, c + 1));
			}
		}
        return entity;
	}
	
	public Object getResultSetValue(ResultSet rs, int index) throws SQLException {
		Object obj = rs.getObject(index);
		String className = null;
		if (obj != null) {
			className = obj.getClass().getName();
		}
		if (obj instanceof Blob) {
			obj = rs.getBytes(index);
		} else if (obj instanceof Clob) {
			obj = rs.getString(index);
		} else if (className != null &&
				("oracle.sql.TIMESTAMP".equals(className) || "oracle.sql.TIMESTAMPTZ".equals(className))) {
			obj = rs.getTimestamp(index);
		} else if (className != null && className.startsWith("oracle.sql.DATE")) {
			
			String metaDataClassName = rs.getMetaData().getColumnClassName(index);
			if ("java.sql.Timestamp".equals(metaDataClassName) ||
					"oracle.sql.TIMESTAMP".equals(metaDataClassName)) {
				obj = rs.getTimestamp(index);
			} else {
				obj = rs.getDate(index);
			}
		} else if (obj != null && obj instanceof java.sql.Date) {
			if ("java.sql.Timestamp".equals(rs.getMetaData().getColumnClassName(index))) {
				obj = rs.getTimestamp(index);
			}
		}
		return obj;
	}
}
