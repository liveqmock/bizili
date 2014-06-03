package com.vteba.tm.jdbc.spring;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

public class ColumnRowMapper<T> implements RowMapper<T> {
	private List<String> columns;
	
	public ColumnRowMapper(List<String> columns) {
		this.columns = columns;
	}
	
	@Override
	public T mapRow(ResultSet rs, int rowNum) throws SQLException {
		T t;
		for (String col : columns) {
			
		}
		return null;
	}

}
