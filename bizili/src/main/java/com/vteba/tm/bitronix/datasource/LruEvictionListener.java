package com.vteba.tm.bitronix.datasource;

/**
 * Eviction listener interface for {@link LruStatementCache}.
 *
 * @author lorban
 */
public interface LruEvictionListener {

    public void onEviction(Object value);

}
