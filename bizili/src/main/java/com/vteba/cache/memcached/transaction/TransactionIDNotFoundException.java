package com.vteba.cache.memcached.transaction;

/**
 * This exception is used when a transaction ID suddenly disappeared when trying to update its state.
 * This usually means that the ID was duplicated one way or another (ie: illegal branch joins in the case of XA and XID transaction ID)
 * and the state was cleaned up somewhere earlier.
 *
 * @author Ludovic Orban
 */
public class TransactionIDNotFoundException extends TransactionException {

	private static final long serialVersionUID = 1L;

	/**
     * Constructor
     */
    public TransactionIDNotFoundException(String message) {
        super(message);
    }
}
