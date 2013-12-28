package com.vteba.tm.bitronix.infinispan;

import java.util.Set;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.StringRefAddr;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;

import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.transaction.xa.TransactionXaAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bitronix.tm.internal.XAResourceHolderState;
import bitronix.tm.recovery.RecoveryException;
import bitronix.tm.resource.ResourceObjectFactory;
import bitronix.tm.resource.common.ResourceBean;
import bitronix.tm.resource.common.XAResourceHolder;
import bitronix.tm.resource.common.XAResourceProducer;
import bitronix.tm.resource.common.XAStatefulHolder;

/**
 * Infinispan XAResourceProducer。将Infinispan作为Bitronix的XAResource。
 * @author yinlei
 * date 2013-6-30 下午5:33:06
 */
public class InfinispanXAResourceProducer implements XAResourceProducer {
	private static final long serialVersionUID = -9098631122100996519L;
	private ResourceBean bean = new InfinispanResourceBean();
	private static Logger logger = LoggerFactory.getLogger(InfinispanXAResourceProducer.class);
	private EmbeddedCacheManager cacheManager;
	
    public InfinispanXAResourceProducer() {
    }

    public void setUniqueName(String uniqueName) {
        bean.setUniqueName(uniqueName);
    }

    @Override
    public String getUniqueName() {
        return bean.getUniqueName();
    }

    @Override
    public XAResourceHolderState startRecovery() throws RecoveryException {
        return new XAResourceHolderState(new NoRecoveryXAResourceHolder(bean), bean);
    }

    @Override
    public void endRecovery() throws RecoveryException {
    }

    @Override
    public void setFailed(boolean b) {
    }

    @Override
    public XAResourceHolder findXAResourceHolder(XAResource xaResource) {
    	Set<String> cacheNames = cacheManager.getCacheNames();
    	
    	for (String cacheName : cacheNames) {
    		if (cacheManager.getCacheConfiguration(cacheName).transaction().useSynchronization()) {//使用同步策略的
    			continue;
    		}
    		
    		TransactionXaAdapter txXaAdapter = (TransactionXaAdapter) cacheManager.getCache(cacheName).getAdvancedCache().getXAResource();
    		try {
    			if (txXaAdapter.isSameRM(xaResource)) {
        			return new InfinispanXAResourceHolder((TransactionXaAdapter)xaResource, bean);
        		}
			} catch (XAException e) {
				logger.info("判断InfinispanXaResource和当前xaResource是否相同异常，" + e.getMessage());
			}
    		
    		//XaTransactionTable txTable = (XaTransactionTable)cacheManager.getCache(cacheName).getAdvancedCache().getComponentRegistry().getComponent(TransactionTable.class);
    		
//    		TransactionTable txTable = cacheManager.getCache(cacheName).getAdvancedCache().getComponentRegistry().getComponent(TransactionTable.class);
//    		Collection<LocalTransaction> localTransactions = txTable.getLocalTransactions();
//    		
//    		for (Object localTransaction : localTransactions) {
//    			TransactionXaAdapter xaAdapter = (TransactionXaAdapter) localTransaction;
//    			if (xaResource == xaAdapter) {
//        			return new InfinispanXAResourceHolder(xaAdapter, bean);
//        		}
//    		}
    		
//    		TransactionXaAdapter xaAdapter = (TransactionXaAdapter)cacheManager.getCache(cacheName).getAdvancedCache().getXAResource();
//    		if (xaResource == xaAdapter) {
//    			return new InfinispanXAResourceHolder(xaAdapter, bean);
//    		}
    		
    	}
    	
//    	for (String cacheName : cacheNames) {
//    		Cache<?, ?> cache = cacheManager.getCache(cacheName);
//    		TransactionTable transactionTable = (TransactionTable) FieldExtractionHelper.extractComponent(cache, TransactionTable.class);
//    		ConcurrentMap<Transaction, LocalTransaction> localTxs = (ConcurrentMap<Transaction, LocalTransaction>) FieldExtractionHelper.extractField(TransactionTable.class, transactionTable, "localTransactions");
//            
//            for (Entry<Transaction, LocalTransaction> txEntry : localTxs.entrySet()) {
//                
//                LocalTransaction adapter = txEntry.getValue();
//
//                if (adapter == xaResource) {
//                    InfinispanXAResourceHolder holder = new InfinispanXAResourceHolder(adapter, bean);
//                    return holder;
//                }
//            }
//    	}
//        Map cachesMap = (Map) FieldExtractionHelper.extractField(manager.getClass(), manager, "caches");
//
//        Iterator cachesIt = cachesMap.entrySet().iterator();
//        while (cachesIt.hasNext()) {
//            Map.Entry cacheEntry = (Map.Entry) cachesIt.next();
//            Cache cache = (Cache) cacheEntry.getValue();//获得缓存
//
//            TransactionTable transactionTable = (TransactionTable) FieldExtractionHelper.extractComponent(cache, TransactionTable.class);
//            Map localTxs = (Map) FieldExtractionHelper.extractField(TransactionTable.class, transactionTable, "localTransactions");
//            Iterator txs = localTxs.entrySet().iterator();
//            while (txs.hasNext()) {
//                Map.Entry txEntry = (Map.Entry) txs.next();
//                TransactionXaAdapter adapter = (TransactionXaAdapter) txEntry.getValue();
//
//                if (adapter == xaResource) {
//                    InfinispanXAResourceHolder holder = new InfinispanXAResourceHolder(adapter, bean);
//                    
//                    //holder.setXAResourceHolderState(new XAResourceHolderState(holder, bean));
//                    return holder;
//                }
//            }
//        }
        return null;
    }

    @Override
    public void init() {
    }

    @Override
    public void close() {
    }

    @Override
    public XAStatefulHolder createPooledConnection(Object o, ResourceBean resourceBean) throws Exception {
        throw new UnsupportedOperationException("non-pooled resource");
    }

    @Override
    public Reference getReference() throws NamingException {
        return new Reference(
                InfinispanXAResourceProducer.class.getName(),
                new StringRefAddr("uniqueName", getUniqueName()),
                ResourceObjectFactory.class.getName(),
                null);
    }

	/**
	 * @return the cacheManager
	 */
	public EmbeddedCacheManager getCacheManager() {
		return cacheManager;
	}

	/**
	 * @param cacheManager the cacheManager to set
	 */
	public void setCacheManager(EmbeddedCacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}
}
