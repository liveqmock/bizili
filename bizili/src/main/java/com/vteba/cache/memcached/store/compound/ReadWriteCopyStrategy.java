package com.vteba.cache.memcached.store.compound;

import java.io.Serializable;

/**
 * @param <T> type
 * @since 2.4.0
 * @author Ludovic Orban
 */
public interface ReadWriteCopyStrategy<T> extends Serializable {

    /**
     * Deep copies some object and returns an internal storage-ready copy
     *
     * @param value the value to copy
     * @return the storage-ready copy
     */
    T copyForWrite(final T value);

    /**
     * Reconstruct an object from its storage-ready copy.
     *
     * @param storedValue the storage-ready copy
     * @return the original object
     */
    T copyForRead(final T storedValue);
}
