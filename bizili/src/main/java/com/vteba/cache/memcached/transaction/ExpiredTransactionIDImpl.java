package com.vteba.cache.memcached.transaction;

/**
 * @author Ludovic Orban
 */
public class ExpiredTransactionIDImpl extends TransactionIDImpl {

	private static final long serialVersionUID = 1L;

	/**
     * Create an expired transaction ID
     * @param transactionId the non-expired transaction ID to copy
     */
    public ExpiredTransactionIDImpl(TransactionIDImpl transactionId) {
      super(transactionId);
    }

}
