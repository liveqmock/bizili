package com.vteba.cache.memcached.transaction.xa;

/**
 * Listener interface which provides callback hooks for listening to the 2PC lifecycle
 *
 * @author Ludovic Orban
 */
public interface XAExecutionListener {

    /**
     * Called when the resource is about to prepare
     * @param xaResource the XAResource about to prepare
     */
    void beforePrepare(EhcacheXAResource xaResource);

    /**
     * Called when the resource committed or rolled back
     * @param xaResource the XAResource which committed or rolled back
     */
    void afterCommitOrRollback(EhcacheXAResource xaResource);

}
