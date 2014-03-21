package com.vteba.tm.jdbc.spring;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.vteba.lang.bytecode.ConstructorAccess;
import com.vteba.lang.bytecode.FieldAccess;
import com.vteba.tm.hibernate.transformer.ColumnAliasParser;
import com.vteba.util.reflection.AsmUtils;

/**
 * Spring JdbcTemplate 通用RowMapper，通过访问field实现。
 * @author yinlei
 * date 2013-6-25 下午10:20:06
 * @param <T> RowMapper要转换的对象
 */
public class FieldRowMapper<T> implements RowMapper<T> {
	private Class<T> resultClass;
	private ConstructorAccess<T> constructorAccess;
	private FieldAccess fieldAccess;
	private String[] columnLabels;
	private Class<?>[] fieldTypes;
	private int[] fieldIndexes;
	
	public FieldRowMapper(Class<T> resultClass, String sql) {
		this.resultClass = resultClass;
		constructorAccess = AsmUtils.get().createConstructorAccess(this.resultClass);
		fieldAccess = AsmUtils.get().createFieldAccess(resultClass);
		
		RowMapInfo rowMapInfo = RowMapInfoCache.getInstance().get(sql);
		if (rowMapInfo != null) {
			columnLabels = rowMapInfo.getColumnLabels();
			fieldTypes = rowMapInfo.getFieldTypes();
			fieldIndexes = rowMapInfo.getFieldIndexes();
		} else {
			String[] fieldNames = fieldAccess.getFieldNames();
			Class<?>[] fieldType = fieldAccess.getFieldTypes();
			columnLabels = ColumnAliasParser.get().parseColumnAlias(sql, true);
			int columnCount = columnLabels.length;
			this.fieldTypes = new Class<?>[columnCount];
			this.fieldIndexes = new int[columnCount];
			for (int i = 0; i < columnCount; i++) {
				String column = columnLabels[i];
				int fieldLen = fieldNames.length;
				for (int j = 0; j <  fieldLen; j++) {
					String fieldName = fieldNames[j];
					if (column.equals(fieldName)) {
						this.fieldTypes[i] = fieldType[j];
						this.fieldIndexes[i] = fieldAccess.getIndex(fieldName);
					}
				}
			}
			rowMapInfo = new RowMapInfo();
			rowMapInfo.setColumnLabels(columnLabels);
			rowMapInfo.setFieldIndexes(fieldIndexes);
			rowMapInfo.setFieldTypes(fieldTypes);
			RowMapInfoCache.getInstance().put(sql, rowMapInfo);
		}
	}
	
	@Override
	public T mapRow(ResultSet rs, int rowNum) throws SQLException {
		T entity = constructorAccess.newInstance();
		int columnCount = fieldIndexes.length;
		for (int i = 0; i < columnCount; i++) {
			Object value = getResultSetValue(rs, i + 1);//getValue(rs, fieldTypes[i], columnLabels[i]);
			fieldAccess.set(entity, fieldIndexes[i], value);
		}
        return entity;
	}
	
	public Object getValue(ResultSet rs, Class<?> clazz, String columnLabel) {
		try {
			if (clazz == String.class) {
				return rs.getString(columnLabel);
			} else if (clazz == Integer.class || clazz == Integer.TYPE) {
				return rs.getInt(columnLabel);
			} else if (clazz == Long.class || clazz == Long.TYPE) {
				return rs.getLong(columnLabel);
			} else if (clazz == Double.class || clazz == Double.TYPE) {
				return rs.getDouble(columnLabel);
			} else if (clazz == Date.class || clazz == java.sql.Date.class) {
				return rs.getDate(columnLabel);
			} else if (clazz == Boolean.class || clazz == Boolean.TYPE) {
				return rs.getBoolean(columnLabel);
			} else if (clazz == Float.class || clazz == Float.TYPE) {
				return rs.getFloat(columnLabel);
			} else if (clazz == BigInteger.class) {
				return rs.getBigDecimal(columnLabel).toBigInteger();
			} else if (clazz == BigDecimal.class) {
				return rs.getBigDecimal(columnLabel);
			} else if (clazz == Short.class || clazz == Short.TYPE) {
				return rs.getShort(columnLabel);
			} else if (clazz == Byte.class || clazz == Byte.TYPE) {
				return rs.getByte(columnLabel);
			}
		} catch (SQLException e) {
			
		}
		return null;
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
