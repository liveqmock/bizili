package com.vteba.cache.memcached.pool;

import java.util.Collection;

/**
 * PoolEvictors are responsible for finding the best candidates in a collection of resources using a shared
 * resource pool and performing eviction on them.
 *
 * @param <T> The type of the resources to free space on.
 * @author Ludovic Orban
 */
public interface PoolEvictor<T> {

    /**
     * Free at least N bytes from a collection of resources
     *
     * @param from a collection of resources to free from
     * @param bytes the number of bytes to free up
     * @return true if at least N bytes could be freed
     */
    boolean freeSpace(Collection<T> from, long bytes);

}
