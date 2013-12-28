package com.vteba.cache.memcached.transaction.manager.selector;

//import java.lang.reflect.Method;

//import javax.transaction.xa.XAResource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bitronix.tm.resource.ehcache.EhCacheXAResourceProducer;

import com.vteba.cache.memcached.transaction.xa.EhcacheXAResource;

/**
 * A Selector for the Bitronix Transaction Manager.
 *
 * @author Ludovic Orban
 */
public class BitronixSelector extends FactorySelector {
    private static final Logger LOG = LoggerFactory.getLogger(BitronixSelector.class);

    /**
     * Constructor
     */
    public BitronixSelector() {
        super("Bitronix", "bitronix.tm.TransactionManagerServices", "getTransactionManager");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerResource(EhcacheXAResource ehcacheXAResource, boolean forRecovery) {
        String uniqueName = ehcacheXAResource.getCacheName();
        try {
//            Class<?> producerClass = ClassLoaderUtil.loadClass("bitronix.tm.resource.ehcache.EhCacheXAResourceProducer");
//
//            Class<?>[] signature = new Class[] {String.class, XAResource.class};
//            Object[] args = new Object[] {uniqueName, ehcacheXAResource};
//            Method method = producerClass.getMethod("registerXAResource", signature);
//            method.invoke(null, args);
        	EhCacheXAResourceProducer.registerXAResource(uniqueName, ehcacheXAResource);
        } catch (Exception e) {
            LOG.error("unable to register resource of cache " + uniqueName + " with BTM", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unregisterResource(EhcacheXAResource ehcacheXAResource, boolean forRecovery) {
        String uniqueName = ehcacheXAResource.getCacheName();
        try {
//            Class<?> producerClass = ClassLoaderUtil.loadClass("bitronix.tm.resource.ehcache.EhCacheXAResourceProducer");
//            Class<?>[] signature = new Class[] {String.class, XAResource.class};
//            Object[] args = new Object[] {uniqueName, ehcacheXAResource};
//            Method method = producerClass.getMethod("unregisterXAResource", signature);
//            method.invoke(null, args);
        	EhCacheXAResourceProducer.unregisterXAResource(uniqueName, ehcacheXAResource);
        } catch (Exception e) {
            LOG.error("unable to unregister resource of cache " + uniqueName + " with BTM", e);
        }
    }
}
