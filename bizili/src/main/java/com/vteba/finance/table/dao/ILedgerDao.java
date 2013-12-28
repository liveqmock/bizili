package com.vteba.finance.table.dao;

import com.vteba.finance.table.model.Ledger;
import com.vteba.persister.hibernate.IHibernateGenericDao;

/**
 * 总账DAO接口
 * @author yinlei 
 * date 2012-7-6 下午10:31:21
 */
public interface ILedgerDao extends IHibernateGenericDao<Ledger, String> {

}
