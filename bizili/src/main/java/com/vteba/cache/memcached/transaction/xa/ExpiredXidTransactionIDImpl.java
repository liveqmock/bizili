package com.vteba.cache.memcached.transaction.xa;

/**
 * @author Ludovic Orban
 */
public class ExpiredXidTransactionIDImpl extends XidTransactionIDImpl {

	private static final long serialVersionUID = 1L;

	/**
     * Create an expired XidTransactionID from a non-expired copy
     * @param xidTransactionId the non-expired copy
     */
    public ExpiredXidTransactionIDImpl(XidTransactionID xidTransactionId) {
        super(xidTransactionId.getXid(), xidTransactionId.getCacheName());
    }

}
