package com.vteba.cache.memcached.transaction;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * A transaction ID implementation with uniqueness across a single JVM
 *
 * @author Ludovic Orban
 */
public class TransactionIDImpl implements TransactionID {

	private static final long serialVersionUID = 1L;

	private static final AtomicInteger ID_GENERATOR = new AtomicInteger();

    private final int id;

    /**
     * Create a new TransactionIDImpl instance
     */
    public TransactionIDImpl() {
        this.id = ID_GENERATOR.getAndIncrement();
    }

    /**
     * Create a new TransactionIDImpl instance from an existing one
     * @param transactionId the transaction Id to copy
     */
    protected TransactionIDImpl(TransactionIDImpl transactionId) {
        TransactionIDImpl txIdImpl = transactionId;
        this.id = txIdImpl.id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(Object obj) {
        if (obj instanceof TransactionIDImpl) {
            TransactionIDImpl otherId = (TransactionIDImpl) obj;
            return id == otherId.id;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return Integer.toString(id);
    }
}
