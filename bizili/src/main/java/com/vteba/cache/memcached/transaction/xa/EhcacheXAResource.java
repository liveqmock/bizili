package com.vteba.cache.memcached.transaction.xa;

import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.xa.XAResource;

/**
 * EhcacheXAResource represents an {@link com.vteba.cache.hibernate.memcached.Memcache Memcache} instance.
 *
 * @author Nabib El-Rahman
 * @author Alex Snaps
 */
public interface EhcacheXAResource extends XAResource {

    /**
     * Add a listener which will be called back according to the 2PC lifecycle
     * @param listener the XAExecutionListener
     */
    void addTwoPcExecutionListener(XAExecutionListener listener);

    /**
     * Getter to the name of the cache wrapped by this XAResource
     * @return {@link net.sf.ehcache.Ehcache#getName} value
     */
    String getCacheName();

    /**
     * Obtain the already associated {@link XATransactionContext} with the current Transaction,
     * or create a new one should none be there yet.
     * @return The associated Transaction associated {@link XATransactionContext}
     * @throws SystemException Thrown if the associated transaction manager encounters an unexpected error condition.
     * @throws RollbackException Thrown if the resource has to be enlisted with the transaction, while it is marked for rollback only.
     */
    XATransactionContext createTransactionContext() throws SystemException, RollbackException;

    /**
     * Gets the current {@link XATransactionContext} associated with this resource
     * @return the current {@link XATransactionContext}, or null if none
     */
    XATransactionContext getCurrentTransactionContext();
}
