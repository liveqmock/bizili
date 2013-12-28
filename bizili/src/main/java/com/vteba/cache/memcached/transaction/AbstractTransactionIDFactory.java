package com.vteba.cache.memcached.transaction;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import com.vteba.cache.hibernate.memcached.Memcache;
import com.vteba.cache.memcached.transaction.xa.XidTransactionID;

/**
 * An abstract map backed transaction id factory.
 *
 * @author Chris Dennis
 */
public abstract class AbstractTransactionIDFactory implements TransactionIDFactory {

    /**
     * Return the map of transaction states.
     *
     * @return the map of transaction states
     */
    protected abstract ConcurrentMap<TransactionID, Decision> getTransactionStates();

    /**
     * {@inheritDoc}
     */
    @Override
    public void markForCommit(TransactionID transactionID) {
        while (true) {
            Decision current = getTransactionStates().get(transactionID);
            if (current == null) {
                throw new TransactionIDNotFoundException("transaction state of transaction ID [" + transactionID + "] already cleaned up");
            }
            switch (current) {
                case IN_DOUBT:
                    if (getTransactionStates().replace(transactionID, Decision.IN_DOUBT, Decision.COMMIT)) {
                        return;
                    }
                    break;
                case ROLLBACK:
                    throw new IllegalStateException(this + " already marked for rollback, cannot re-mark it for commit");
                case COMMIT:
                    return;
                default:
                    throw new AssertionError("unreachable code");
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void markForRollback(XidTransactionID transactionID) {
        while (true) {
            Decision current = getTransactionStates().get(transactionID);
            if (current == null) {
                throw new TransactionIDNotFoundException("transaction state of transaction ID [" + transactionID + "] already cleaned up");
            }
            switch (current) {
                case IN_DOUBT:
                    if (getTransactionStates().replace(transactionID, Decision.IN_DOUBT, Decision.ROLLBACK)) {
                        return;
                    }
                    break;
                case ROLLBACK:
                    return;
                case COMMIT:
                    throw new IllegalStateException(this + " already marked for commit, cannot re-mark it for rollback");
                default:
                    throw new AssertionError();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDecisionCommit(TransactionID transactionID) {
        return Decision.COMMIT.equals(getTransactionStates().get(transactionID));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear(TransactionID transactionID) {
        getTransactionStates().remove(transactionID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<XidTransactionID> getAllXidTransactionIDsFor(Memcache cache) {
        String cacheName = cache.getName();
        Set<XidTransactionID> result = new HashSet<XidTransactionID>();

        for (TransactionID id : getTransactionStates().keySet()) {
            if (id instanceof XidTransactionID) {
                XidTransactionID xid = (XidTransactionID) id;
                if (cacheName.equals(xid.getCacheName())) {
                    result.add(xid);
                }
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<TransactionID> getAllTransactionIDs() {
        return Collections.unmodifiableSet(getTransactionStates().keySet());
    }
}
