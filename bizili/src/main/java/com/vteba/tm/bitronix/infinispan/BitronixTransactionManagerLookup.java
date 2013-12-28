package com.vteba.tm.bitronix.infinispan;

import bitronix.tm.TransactionManagerServices;
import org.infinispan.transaction.lookup.TransactionManagerLookup;

import javax.transaction.TransactionManager;

/**
 * Infinispan TransactionManagerLookup，Bitronix实现。
 * @author yinlei
 * date 2013-6-30 下午5:32:58
 */
public class BitronixTransactionManagerLookup implements TransactionManagerLookup {
    
	public BitronixTransactionManagerLookup() {
		super();
	}

	@Override
    public TransactionManager getTransactionManager() throws Exception {
        return TransactionManagerServices.getTransactionManager();
    }
}
