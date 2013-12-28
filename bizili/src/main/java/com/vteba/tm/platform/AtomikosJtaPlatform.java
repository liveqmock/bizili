package com.vteba.tm.platform;

import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

import org.hibernate.service.jta.platform.internal.AbstractJtaPlatform;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;

/**
 * Atomikos JTA transaction platform, hibernate use this locate JTA transaction
 * @author yinlei 
 * date 2012-5-23 下午6:56:23
 */
public class AtomikosJtaPlatform extends AbstractJtaPlatform {

	private static final long serialVersionUID = 8596961280112400447L;
	
//	private transient TransactionManager transactionManager;
//	private transient UserTransaction userTransaction;
//	
//	@Override
//	protected synchronized TransactionManager locateTransactionManager() {
//		if (transactionManager == null) {
//			transactionManager = new UserTransactionManager();
//		}
//		return transactionManager;
//	}
//
//	@Override
//	protected synchronized UserTransaction locateUserTransaction() {
//		if (userTransaction == null) {
//			userTransaction = new UserTransactionImp();
//		}
//		return userTransaction;
//	}
	
	private transient TransactionManager transactionManager = new UserTransactionManager();;
	private transient UserTransaction userTransaction = new UserTransactionImp();
	
	@Override
	protected TransactionManager locateTransactionManager() {
		return transactionManager;
	}

	@Override
	protected UserTransaction locateUserTransaction() {
		return userTransaction;
	}

}
