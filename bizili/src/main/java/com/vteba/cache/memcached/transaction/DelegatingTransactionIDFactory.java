package com.vteba.cache.memcached.transaction;

import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import javax.transaction.xa.Xid;

import com.vteba.cache.hibernate.memcached.Memcache;
import com.vteba.cache.memcached.transaction.xa.XidTransactionID;

/**
 * A TransactionIDFactory implementation with delegates calls to either a clustered
 * or non-clustered factory
 *
 * @author Ludovic Orban
 */
public class DelegatingTransactionIDFactory implements TransactionIDFactory {

    //private final FeaturesManager featuresManager;
    //private final TerracottaClient terracottaClient;
    ///private final String cacheManagerName;
    //private volatile ClusteredInstanceFactory clusteredInstanceFactory;
    private volatile AtomicReference<TransactionIDFactory> transactionIDFactory = new AtomicReference<TransactionIDFactory>();

    /**
     * Create a new DelegatingTransactionIDFactory
     *
     * @param terracottaClient a terracotta client
     * @param cacheManagerName the name of the cache manager which creates this.
     */
    public DelegatingTransactionIDFactory(String cacheManagerName){//FeaturesManager featuresManager, TerracottaClient terracottaClient, String cacheManagerName) {
        //this.featuresManager = featuresManager;
        //this.terracottaClient = terracottaClient;
        ///this.cacheManagerName = cacheManagerName;
    }

    private TransactionIDFactory get() {
//        ClusteredInstanceFactory cif = terracottaClient.getClusteredInstanceFactory();
//        if (cif != null && cif != this.clusteredInstanceFactory) {
//            this.transactionIDFactory.set(cif.createTransactionIDFactory(UUID.randomUUID().toString(), cacheManagerName));
//            this.clusteredInstanceFactory = cif;
//        }

        if (transactionIDFactory.get() == null) {
            TransactionIDFactory constructed = new TransactionIDFactoryImpl();
//            if (featuresManager == null) {
//                constructed = new TransactionIDFactoryImpl();
//            } else {
//                //constructed = featuresManager.createTransactionIDFactory();
//            }
            if (transactionIDFactory.compareAndSet(null, constructed)) {
                return constructed;
            } else {
                return transactionIDFactory.get();
            }
        } else {
            return transactionIDFactory.get();
        }
    }

    /**
     * {@inheritDoc}
     */
    public TransactionID createTransactionID() {
        return get().createTransactionID();
    }

    /**
     * {@inheritDoc}
     */
    public TransactionID restoreTransactionID(TransactionIDSerializedForm serializedForm) {
        return get().restoreTransactionID(serializedForm);
    }

    /**
     * {@inheritDoc}
     */
    public XidTransactionID createXidTransactionID(Xid xid, Memcache cache) {
        return get().createXidTransactionID(xid, cache);
    }

    /**
     * {@inheritDoc}
     */
    public XidTransactionID restoreXidTransactionID(XidTransactionIDSerializedForm serializedForm) {
        return get().restoreXidTransactionID(serializedForm);
    }

    @Override
    public void markForCommit(TransactionID transactionID) {
        get().markForCommit(transactionID);
    }

    @Override
    public void markForRollback(XidTransactionID transactionID) {
        get().markForRollback(transactionID);
    }

    @Override
    public boolean isDecisionCommit(TransactionID transactionID) {
        return get().isDecisionCommit(transactionID);
    }

    @Override
    public void clear(TransactionID transactionID) {
        get().clear(transactionID);
    }

    @Override
    public Set<XidTransactionID> getAllXidTransactionIDsFor(Memcache cache) {
        return get().getAllXidTransactionIDsFor(cache);
    }

    @Override
    public Set<TransactionID> getAllTransactionIDs() {
        return get().getAllTransactionIDs();
    }

    @Override
    public boolean isExpired(TransactionID transactionID) {
        return get().isExpired(transactionID);
    }

    @Override
    public Boolean isPersistent() {
        if (transactionIDFactory.get() == null) {
            return null;
        } else {
            return get().isPersistent();
        }
    }
}
