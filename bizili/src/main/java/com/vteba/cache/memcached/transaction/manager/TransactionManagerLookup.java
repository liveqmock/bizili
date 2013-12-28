package com.vteba.cache.memcached.transaction.manager;

import javax.transaction.TransactionManager;

import com.vteba.cache.memcached.transaction.xa.EhcacheXAResource;

import java.util.Properties;

/**
 * Interface to enable a XA transactional cache to access the JTA TransactionManager.
 * The implementing class can be configured in your xml file. It will then be instanciated by the Cache, during
 * {@link net.sf.ehcache.Cache#initialise() initialization}. It'll then have the properties injected, should any have been specified. And finally,
 * the TransactionManager will be queried for using #getTransactionManager.
 *
 * @author Alex Snaps
 */
public interface TransactionManagerLookup {

    /**
     * Switch the TransactionManagerLookup implementation to its initialized state.
     * All EhcacheXAResources registered before initialization are queued up internally
     * and are only registered with the transaction manager during initialization.
     */
    void init();

    /**
     * Lookup available txnManagers
     * @return TransactionManager
     */
    TransactionManager getTransactionManager();
    
    /**
     * 设置事务管理器，在已知事务管理器的情况下，可以减少反射的开销 
     * @param transactionManager
     * @author yinlei
     * date 2013-4-5 下午1:25:04
     */
    void setTransactionManager(TransactionManager transactionManager);

    /**
     * execute txnManager specific code to register the XAResource for recovery.
     * @param resource the XAResource to register for recovery in the choosen TM.
     * @param forRecovery true if the XAResource is meant to be registered for recovery purpose only.
     */
    void register(EhcacheXAResource resource, boolean forRecovery);

    /**
     * execute txnManager specific code to unregister the XAResource for recovery.
     * @param resource the XAResource to register for recovery in the choosen TM.
     * @param forRecovery true if the XAResource is meant to be registered for recovery purpose only.
     */
    void unregister(EhcacheXAResource resource, boolean forRecovery);

    /**
     * Setter to the properties properties. This will be called right after the class has been instantiated.
     *
     * @param properties the properties parsed from the config file's
     *                   transactionManagerLookup tag's properties attribute
     */
    void setProperties(Properties properties);
}
