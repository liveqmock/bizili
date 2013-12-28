package com.vteba.cache.memcached.transaction.xa;

import com.vteba.cache.memcached.transaction.TransactionException;

/**
 * This exception is used internally when an optimistic lock failed, ie:
 * when the expected previous value is not found at commit time.
 *
 * @author Ludovic Orban
 */
public class OptimisticLockFailureException extends TransactionException {

	private static final long serialVersionUID = 1L;

	/**
     * Constructor
     */
    public OptimisticLockFailureException() {
        super("");
    }
}
