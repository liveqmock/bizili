package com.vteba.cache.memcached.transaction;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.transaction.xa.Xid;

import com.vteba.cache.hibernate.memcached.Memcache;
import com.vteba.cache.memcached.transaction.xa.XidTransactionID;
import com.vteba.cache.memcached.transaction.xa.XidTransactionIDImpl;

/**
 * A TransactionIDFactory implementation with uniqueness across a single JVM

 * @author Ludovic Orban
 */
public class TransactionIDFactoryImpl extends AbstractTransactionIDFactory {

    private final ConcurrentMap<TransactionID, Decision> transactionStates = new ConcurrentHashMap<TransactionID, Decision>();

    /**
     * {@inheritDoc}
     */
    public TransactionID createTransactionID() {
        TransactionID id = new TransactionIDImpl();
        getTransactionStates().putIfAbsent(id, Decision.IN_DOUBT);
        return id;
    }

    /**
     * {@inheritDoc}
     */
    public TransactionID restoreTransactionID(TransactionIDSerializedForm serializedForm) {
        throw new UnsupportedOperationException("unclustered transaction IDs are directly deserializable!");
    }

    /**
     * {@inheritDoc}
     */
    public XidTransactionID createXidTransactionID(Xid xid, Memcache cache) {
        XidTransactionID id = new XidTransactionIDImpl(xid, cache.getName());
        getTransactionStates().putIfAbsent(id, Decision.IN_DOUBT);
        return id;
    }

    /**
     * {@inheritDoc}
     */
    public XidTransactionID restoreXidTransactionID(XidTransactionIDSerializedForm serializedForm) {
        throw new UnsupportedOperationException("unclustered transaction IDs are directly deserializable!");
    }

    @Override
    protected ConcurrentMap<TransactionID, Decision> getTransactionStates() {
        return transactionStates;
    }

    @Override
    public Boolean isPersistent() {
        return Boolean.FALSE;
    }

    @Override
    public boolean isExpired(TransactionID transactionID) {
        return false;
    }
}
