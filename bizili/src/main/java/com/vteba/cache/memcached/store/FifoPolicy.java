package com.vteba.cache.memcached.store;

import com.vteba.cache.memcached.Element;


/**
 * Contains common LFU policy code for use between the LfuMemoryStore and the DiskStore, which also
 * uses an LfuPolicy for evictions.
 *
 * @author Greg Luck
 * @version $Id: FifoPolicy.java 5631 2012-05-10 08:31:33Z teck $
 */
public class FifoPolicy extends AbstractPolicy {

    /**
     * The name of this policy as a string literal
     */
     public static final String NAME = "FIFO";

    /**
     * @return the name of the Policy. Inbuilt examples are LRU, LFU and FIFO.
     */
    public String getName() {
        return NAME;
    }

    /**
     * Compares the desirableness for eviction of two elements
     *
     * Compares hit counts. If both zero,
     *
     * @param element1 the element to compare against
     * @param element2 the element to compare
     * @return true if the second element is preferable to the first element for ths policy
     */
    public boolean compare(Element element1, Element element2) {
        return element2.getLatestOfCreationAndUpdateTime() < element1.getLatestOfCreationAndUpdateTime();

    }

}
