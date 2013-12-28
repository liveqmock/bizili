package com.vteba.cache.memcached.transaction;

import java.io.Serializable;

import com.vteba.cache.memcached.Element;

/**
 * A soft lock ID is used to uniquely identify a soft lock
 *
 * @author Ludovic Orban
 */
public final class SoftLockID implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final int PRIME = 31;

    private final TransactionID transactionID;
    private final String key;
    private final Element newElement;
    private final Element oldElement;
    private final boolean wasPinned;

    /**
     * Create a new SoftLockID instance
     * @param transactionID the transaction ID
     * @param key the element's key this soft lock is going to protect
     * @param newElement the new element, can be null
     * @param oldElement the old element, can be null
     * @param wasPinned true if the key whose element is about to be replaced by this soft lock was pinned in the underlying store
     */
    public SoftLockID(TransactionID transactionID, String key, Element newElement, Element oldElement, boolean wasPinned) {
        this.transactionID = transactionID;
        this.key = key;
        this.newElement = newElement;
        this.oldElement = oldElement;
        this.wasPinned = wasPinned;
    }

    /**
     * Check if the key was pinned in the underlying store before its element was replaced by this soft lock
     * @return true if the key was pinned, false otherwise
     */
    public boolean wasPinned() {
        return wasPinned;
    }

    /**
     * Get the ID of the transaction under which this soft lock is operating
     * @return the TransactionID
     */
    public TransactionID getTransactionID() {
        return transactionID;
    }

    /**
     * Get the key of the element this soft lock is guarding
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * Get the Element with which this soft lock should be replaced by on commit.
     * @return the commit Element
     */
    public Element getNewElement() {
        return newElement;
    }

    /**
     * Get the Element with which this soft lock should be replaced by on rollback.
     * @return the rollback Element
     */
    public Element getOldElement() {
        return oldElement;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hashCode = PRIME;

        hashCode *= transactionID.hashCode();
        hashCode *= key.hashCode();

        return hashCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object object) {
        if (object instanceof SoftLockID) {
            SoftLockID other = (SoftLockID) object;

            if (!transactionID.equals(other.transactionID)) {
                return false;
            }

            if (!key.equals(other.key)) {
                return false;
            }

            return true;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Soft Lock ID [transactionID: " + transactionID + ", key: " + key + "]";
    }

}
