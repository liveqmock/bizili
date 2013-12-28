package com.vteba.cache.memcached.transaction;

/**
 * This exception is thrown when a transactional operation times out
 *
 * @author Ludovic Orban
 */
public class TransactionTimeoutException extends TransactionException {

	private static final long serialVersionUID = 1L;

	/**
     * Create a new TransactionTimeoutException
     * @param message the error message
     */
    public TransactionTimeoutException(String message) {
        super(message);
    }
}
