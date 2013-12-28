package com.vteba.cache.memcached.transaction;

/**
 * The decision types a Transaction ID can be in
 *
 * @author Ludovic Orban
 */
public enum Decision {

    /**
     * Transaction decision not yet made.
     */
    IN_DOUBT,

    /**
     * Transaction has been marked for commit.
     */
    COMMIT,

    /**
     * Transaction has been marked for rollback.
     */
    ROLLBACK
}