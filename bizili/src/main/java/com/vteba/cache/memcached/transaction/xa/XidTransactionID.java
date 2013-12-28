package com.vteba.cache.memcached.transaction.xa;

import com.vteba.cache.memcached.transaction.TransactionID;

import javax.transaction.xa.Xid;

/**
 * A special TransactionID using a XID internally
 *
 * @author Ludovic Orban
 */
public interface XidTransactionID extends TransactionID {

    /**
     * Get the XID of this transaction ID
     * @return the XID
     */
    Xid getXid();

    /**
     * Get the name of the associated Ehcache resource.
     *
     * @return the Ehcache resource name
     */
    String getCacheName();
}
