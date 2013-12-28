package com.vteba.cache.memcached.transaction;

/**
 * This exception is thrown when a deadlock between two transactions is detected
 *
 * @author Ludovic Orban
 */
public class DeadLockException extends TransactionException {

	private static final long serialVersionUID = 1L;

	/**
     * Create a new DeadLockException
     * @param message the error message
     */
    public DeadLockException(String message) {
        super(message);
    }

}
