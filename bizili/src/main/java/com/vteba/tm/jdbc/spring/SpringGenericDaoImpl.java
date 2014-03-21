package com.vteba.tm.jdbc.spring;

import java.io.Serializable;

import com.vteba.tm.jdbc.spi.SpringGenericDao;

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
		GenericRowMapper<T> rowMapper = new GenericRowMapper<T>(entityClass, SELECT);
		springJdbcTemplate.queryForObject(SELECT, rowMapper);
		return null;
	}

	@Override
	public void delete(T entity) {
		
	}
	
	@Override
	public void delete(ID id) {
		springJdbcTemplate.update(DELETE, id);
		
	}

}
