package com.vteba.cache.memcached.store;

import java.util.EventListener;

/**
 * @author gkeim
 */
public interface StoreListener extends EventListener {

    /**
     * Inform of cluster coherence.
     *
     * @param clusterCoherent
     */
    void clusterCoherent(boolean clusterCoherent);
}
