package com.vteba.tx.bitronix.datasource;

/**
 * Eviction listener interface for {@link LruStatementCache}.
 *
 * @author lorban
 */
public interface LruEvictionListener {

    public void onEviction(Object value);

}
