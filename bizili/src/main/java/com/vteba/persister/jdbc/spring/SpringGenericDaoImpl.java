package com.vteba.persister.jdbc.spring;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.vteba.persister.jdbc.spi.SpringGenericDao;

public class SpringGenericDaoImpl<T, ID extends Serializable> implements SpringGenericDao<T, ID> {
	protected String tableName;
	protected Class<T> entityClass;
	
	private String INSERT = "";
	private String DELETE = "";
	private String UPDATE = "";
	private String SELECT = "";
	
	private SpringJdbcTemplate springJdbcTemplate;
	
	public SpringGenericDaoImpl() {
		super();
	}

	public SpringGenericDaoImpl(String tableName, Class<T> entityClass) {
		super();
		this.tableName = tableName;
		this.entityClass = entityClass;
	}

	@Override
	public ID save(T entity) {
		springJdbcTemplate.update(INSERT, entity);
		return null;
	}

	@Override
	public void update(T entity) {
		springJdbcTemplate.update(UPDATE, entity);
		
	}

	@Override
	public T get(ID id) {
		springJdbcTemplate.queryForObject(SELECT, new RowMapper<T>() {

			@Override
			public T mapRow(ResultSet rs, int rowNum) throws SQLException {
				
				return null;
			}
		});
		return null;
	}

	@Override
	public void delete(T entity) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void delete(ID id) {
		springJdbcTemplate.update(DELETE, id);
		
	}

}
