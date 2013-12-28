package com.vteba.cache.memcached.transaction.xa.commands;

import com.vteba.cache.memcached.Store;
import com.vteba.cache.memcached.store.ElementValueComparator;
import com.vteba.cache.memcached.transaction.SoftLockManager;
import com.vteba.cache.memcached.transaction.xa.XidTransactionID;

/**
 * @author Ludovic Orban
 */
public interface Command {

    /**
     * Is this command represents adding a key to the store
     * @param key the key
     * @return true, if this command would try to add an Element for key, otherwise false
     */
    public boolean isPut(Object key);

    /**
     * Is this command represents removing a key to the store
     * @param key the key
     * @return true, if this command would try to remove an Element for key, otherwise false
     */
    public boolean isRemove(Object key);

    /**
     * Prepare the commmand un the underlying store
     * @param store the underdyling store
     * @param softLockManager the soft lock manager
     * @param transactionId the transaction ID
     * @param comparator the element value comparator
     * @return true if prepare updated the store, false otherwise
     */
    boolean prepare(Store store, SoftLockManager softLockManager, XidTransactionID transactionId, ElementValueComparator comparator);

    /**
     * Rollback the prepared change
     * @param store the underlying store
     * @param softLockManager the soft lock manager
     */
    public void rollback(Store store, SoftLockManager softLockManager);

    /**
     * Get the key of the element this command is working on
     * @return the element's key
     */
    String getObjectKey();

}
