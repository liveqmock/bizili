package com.vteba.cache.memcached.transaction;

/**
 * This exception is thrown when a transactional operation got interrupted
 * via Thread.interrupt
 *
 * @author Ludovic Orban
 */
public class TransactionInterruptedException extends TransactionException {

	private static final long serialVersionUID = 1L;

	/**
     * Create a new TransactionInterruptedException
     * @param message the error message
     */
    public TransactionInterruptedException(String message) {
        super(message);
    }
}
