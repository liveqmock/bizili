package com.vteba.cache.memcached.pool;

import com.vteba.cache.memcached.Store;

/**
 * A poolable store reports its resource usage to a {@link Pool}.
 *
 * @author Ludovic Orban
 */
public interface PoolableStore extends Store {

    /**
     * Perform eviction to release on-heap resources
     *
     * @param count the number of elements to evict
     * @param size the size in bytes to free (hint)
     * @return true if the requested number of elements could be evicted
     */
    boolean evictFromOnHeap(int count, long size);

    /**
     * Return the approximate heap hit rate
     *
     * @return the approximate heap hit rate
     */
    float getApproximateHeapHitRate();

    /**
     * Return the approximate heap miss rate
     *
     * @return the approximate heap miss rate
     */
    float getApproximateHeapMissRate();

    /**
     * Return the approximate heap size
     *
     * @return the approximate heap size
     */
    long getApproximateHeapCountSize();

    /**
     * Return the approximate heap size in bytes
     *
     * @return the approximate heap size in bytes
     */
    long getApproximateHeapByteSize();

	long getInMemorySizeInBytes();

}
