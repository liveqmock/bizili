package com.vteba.cache.memcached.transaction.xa;

import javax.transaction.xa.Xid;

/**
 * @author Ludovic Orban
 */
public class XidTransactionIDImpl implements XidTransactionID {

	private static final long serialVersionUID = 1L;
	private final SerializableXid xid;
    private final String cacheName;

    /**
     * Constructor
     * @param xid a XID
     */
    public XidTransactionIDImpl(Xid xid, String cacheName) {
        this.xid = new SerializableXid(xid);
        this.cacheName = cacheName;
    }

    /**
     * {@inheritDoc}
     */
    public Xid getXid() {
        return xid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCacheName() {
        return cacheName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean equals(Object obj) {
        if (obj instanceof XidTransactionIDImpl) {
            XidTransactionIDImpl otherId = (XidTransactionIDImpl) obj;
            return xid.equals(otherId.xid);
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int hashCode() {
        return xid.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Unclustered " + xid;
    }
}
