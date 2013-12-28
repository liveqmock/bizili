package com.vteba.cache.memcached.transaction;

import com.vteba.cache.hibernate.CacheException;

/**
 * Instances of this class or its subclasses are thrown when an error
 * happen in the transaction subsystem
 *
 * @author Ludovic Orban
 */
public class TransactionException extends CacheException {

	private static final long serialVersionUID = -3804786393739915900L;

	/**
     * Create a new TransactionException
     * @param message the error message
     */
    public TransactionException(String message) {
        super(message);
    }

    /**
     * Create a new TransactionException
     * @param message the error message
     * @param throwable the exception's cause
     */
    public TransactionException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
