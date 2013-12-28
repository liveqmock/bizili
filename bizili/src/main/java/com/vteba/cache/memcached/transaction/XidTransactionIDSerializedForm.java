package com.vteba.cache.memcached.transaction;

import java.io.Serializable;

import javax.transaction.xa.Xid;

import com.vteba.cache.memcached.manager.XMemcachedManager;
import com.vteba.cache.memcached.transaction.xa.SerializableXid;

/**
 * A replacement serialized form for XID transaction IDs. It can be used by transaction ID factories
 * to create XID-based IDs that serialize to this form (using writeReplace()) if they don't want or
 * cannot provide directly serializable IDs.
 * <p/>
 * During deserialization, objects of this class will be replaced by the result of the
 * CacheManager.restoreXidTransactionID() call.
 *
 * @author Ludovic Orban
 */
public final class XidTransactionIDSerializedForm implements Serializable {

	private static final long serialVersionUID = 1L;
	private final String cacheManagerName;
    private final String cacheName;
    private final String ownerID;
    private final Xid xid;

    /**
     * Constructor
     *
     * @param cacheManagerName the name of the cache manager which contains the factory
     *                         that created the original XidTransactionID
     * @param cacheName the name of the cache for this id
     * @param xid the XidTransactionID's XID
     */
    public XidTransactionIDSerializedForm(String cacheManagerName, String cacheName, String ownerID, Xid xid) {
        this.cacheManagerName = cacheManagerName;
        this.cacheName = cacheName;
        this.ownerID = ownerID;
        this.xid = new SerializableXid(xid);
    }

    /**
     * Get the name of the cache manager which contains the factory that created the
     * original XidTransactionID
     *
     * @return the cache manager name
     */
    public String getCacheManagerName() {
        return cacheManagerName;
    }

    /**
     * Get the name of the cache which this original XidTransactionID is for.
     *
     * @return the cache name
     */
    public String getCacheName() {
        return cacheName;
    }

    /**
     * Get the original XidTransactionID's owner id
     *
     * @return the original XidTransactionID's owner id
     */
    public String getOwnerID() {
        return ownerID;
    }

    /**
     * Get the original XidTransactionID's XID
     *
     * @return the original XidTransactionID's XID
     */
    public Xid getXid() {
        return xid;
    }

    private Object readResolve() {
    	XMemcachedManager cacheManager = XMemcachedManager.getCacheManager(cacheManagerName);
        if (cacheManager == null) {
            throw new TransactionException("unable to restore XID transaction ID from " + cacheManagerName);
        }
        return cacheManager.getOrCreateTransactionIDFactory().restoreXidTransactionID(this);
    }

}
