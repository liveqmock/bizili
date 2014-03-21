package com.vteba.common.dao.impl;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.SessionFactory;

import com.vteba.common.dao.IModuleMenuDao;
import com.vteba.common.model.ModuleMenu;
import com.vteba.tm.hibernate.impl.HibernateGenericDaoImpl;

/**
 * 菜单模组DAO实现
 * @author yinlei
 * date 2012-5-11 下午7:52:37
 */
@Named
public class ModuleMenuDaoImpl extends HibernateGenericDaoImpl<ModuleMenu, String> implements
		IModuleMenuDao {

	public ModuleMenuDaoImpl() {
		super();
	}

	public ModuleMenuDaoImpl(Class<ModuleMenu> entityClass) {
		super(entityClass);
	}

	@Inject
	public void setSessionFactory(SessionFactory skmbwSessionFactory) {
		this.sessionFactory = skmbwSessionFactory;
	}

}
