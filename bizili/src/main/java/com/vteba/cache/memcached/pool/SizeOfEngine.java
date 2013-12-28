package com.vteba.cache.memcached.pool;

/**
 * SizeOf engines are used to calculate the size of elements stored in poolable stores.
 *
 * @author Ludovic Orban
 */
public interface SizeOfEngine {

    /**
     * Size an element
     *
     * @param key the key of the element
     * @param value the value of the element
     * @param container the container of the element, ie: element object + eventual overhead
     * @return the size of the element in bytes
     */
    Size sizeOf(Object key, Object value, Object container);

    /**
     * Make a copy of the SizeOf engine, preserving all of its internal state but overriding the specified parameters
     *
     * @param maxDepth maximum depth of the object graph to traverse
     * @param abortWhenMaxDepthExceeded true if the object traversal should be aborted when the max depth is exceeded
     * @return a copy of the SizeOf engine using the specified parameters
     */
    SizeOfEngine copyWith(int maxDepth, boolean abortWhenMaxDepthExceeded);

}
