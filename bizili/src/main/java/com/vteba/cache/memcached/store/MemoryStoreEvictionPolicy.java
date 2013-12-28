package com.vteba.cache.memcached.store;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A typesafe enumeration of eviction policies.
 * The policy used to evict elements from the {@link net.sf.ehcache.store.MemoryStore}.
 * This can be one of:                                                                
 * <ol>
 * <li>LRU - least recently used
 * <li>LFU - least frequently used
 * <li>FIFO - first in first out, the oldest element by creation time
 * </ol>
 * The default value is LRU
 *
 * @author <a href="mailto:gluck@thoughtworks.com">Greg Luck</a>
 * @version $Id: MemoryStoreEvictionPolicy.java 5631 2012-05-10 08:31:33Z teck $
 * @since 1.2
 */
public final class MemoryStoreEvictionPolicy implements Serializable {

	private static final long serialVersionUID = -6735783480916540287L;

	/**
     * LRU - least recently used.
     */
    public static final MemoryStoreEvictionPolicy LRU = new MemoryStoreEvictionPolicy("LRU");

    /**
     * LFU - least frequently used.
     */

    public static final MemoryStoreEvictionPolicy LFU = new MemoryStoreEvictionPolicy("LFU");

    /**
     * FIFO - first in first out, the oldest element by creation time.
     */
    public static final MemoryStoreEvictionPolicy FIFO = new MemoryStoreEvictionPolicy("FIFO");

    /**
     * FIFO - first in first out, the oldest element by creation time.
     */
    public static final MemoryStoreEvictionPolicy CLOCK = new MemoryStoreEvictionPolicy("CLOCK");

    private static final Logger LOG = LoggerFactory.getLogger(MemoryStoreEvictionPolicy.class.getName());

    private final String myName;

    /**
     * This class should not be subclassed or have instances created.
     * @param policy
     */
    private MemoryStoreEvictionPolicy(String policy) {
        myName = policy;
    }

    /**
     * @return a String representation of the policy
     */
    @Override
    public String toString() {
        return myName;
    }

    /**
     * Converts a string representation of the policy into a policy.
     *
     * @param policy either LRU, LFU or FIFO
     * @return one of the static instances
     */
    public static MemoryStoreEvictionPolicy fromString(String policy) {
        if (policy != null) {
            if (policy.equalsIgnoreCase("LRU")) {
                return LRU;
            } else if (policy.equalsIgnoreCase("LFU")) {
                return LFU;
            } else if (policy.equalsIgnoreCase("FIFO")) {
                return FIFO;
            } else if (policy.equalsIgnoreCase("CLOCK")) {
                return CLOCK;
            }
        }
            LOG.warn("The memoryStoreEvictionPolicy of {} cannot be resolved. The policy will be set to LRU", policy);
        return LRU;
    }
    
    /**
     * Enum for {@link MemoryStoreEvictionPolicy}
     * 
     */
    public static enum MemoryStoreEvictionPolicyEnum {
        /**
         * Value for {@link MemoryStoreEvictionPolicy#LFU}
         */
        LFU,
        /**
         * Value for {@link MemoryStoreEvictionPolicy#LRU}
         */
        LRU,
        /**
         * Value for {@link MemoryStoreEvictionPolicy#FIFO}
         */
        FIFO;
    }
}
