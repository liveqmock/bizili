package com.vteba.tm.jdbc.spi;

import java.io.Serializable;

/**
 * Spring泛型Dao抽象。
 * @author yinlei
 * date 2013-7-6 下午4:40:09
 */
public interface SpringGenericDao<T, ID extends Serializable> {
	/**
	 * 保存实体entity
	 * @param entity
	 * @return 实体主键
	 */
	public ID save(T entity);
	
	/**
	 * 更新实体entity
	 * @param entity
	 */
	public void update(T entity);
	
	/**
	 * 根据ID get指定entity实体
	 * @param id 主键
	 * @return 实体
	 */
	public T get(ID id);
	
	/**
	 * 根据entity(带主键)删除实体
	 * @param entity
	 */
	public void delete(T entity);
	
	/**
	 * 根据主键删除实体
	 * @param id
	 */
	public int delete(ID id);
}
