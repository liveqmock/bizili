package com.vteba.cache.memcached.transaction;

import java.io.Serializable;

import com.vteba.cache.memcached.manager.XMemcachedManager;

/**
 * A replacement serialized form for transaction IDs. It can be used by transaction ID factories
 * to create IDs that serialize to this form (using writeReplace()) if they don't want or cannot
 * provide directly serializable IDs.
 * <p/>
 * During deserialization, objects of this class will be replaced by the result of the
 * CacheManager.restoreTransactionID() call.
 *
 * @author Ludovic Orban
 */
public final class TransactionIDSerializedForm implements Serializable {

	private static final long serialVersionUID = 1L;
	private final String cacheManagerName;
    private final String clusterUUID;
    private final String ownerID;
    private final long creationTime;
    private final int id;

    /**
     * Constructor
     *
     * @param cacheManagerName the name of the cache manager which contains the factory
     *                         that created the original TransactionID
     * @param clusterUUID the TransactionID's cluster UUID
     * @param creationTime the TransactionID's creation time
     * @param id the TransactionID's internal ID
     */
    public TransactionIDSerializedForm(String cacheManagerName, String clusterUUID, String ownerID, long creationTime, int id) {
        this.cacheManagerName = cacheManagerName;
        this.clusterUUID = clusterUUID;
        this.ownerID = ownerID;
        this.creationTime = creationTime;
        this.id = id;
    }

    /**
     * Get the name of the cache manager which contains the factory that created the
     * original TransactionID
     *
     * @return the cache manager name
     */
    public String getCacheManagerName() {
        return cacheManagerName;
    }

    /**
     * Get the original TransactionID's cluster UUID
     *
     * @return the original TransactionID's cluster UUID
     */
    public String getClusterUUID() {
        return clusterUUID;
    }

    /**
     * Get the original TransactionID's owner id
     *
     * @return the original TransactionID's owner id
     */
    public String getOwnerID() {
        return ownerID;
    }

    /**
     * Get the original TransactionID's creation time
     *
     * @return the original TransactionID's creation time
     */
    public long getCreationTime() {
        return creationTime;
    }

    /**
     * Get the original TransactionID's internal ID
     *
     * @return the original TransactionID's internal ID
     */
    public int getId() {
        return id;
    }

    private Object readResolve() {
    	XMemcachedManager cacheManager = XMemcachedManager.getCacheManager(cacheManagerName);
        if (cacheManager == null) {
            throw new TransactionException("unable to restore transaction ID from " + cacheManagerName);
        }
        return cacheManager.getOrCreateTransactionIDFactory().restoreTransactionID(this);
    }

}
