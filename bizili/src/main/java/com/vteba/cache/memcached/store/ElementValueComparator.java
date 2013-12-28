package com.vteba.cache.memcached.store;

import com.vteba.cache.memcached.Element;

/**
 * Used to compare two element values.
 * Implementations must define a constructor accepting a single CacheConfiguration argument.
 *
 * @author Ludovic Orban
 */
public interface ElementValueComparator {

    /**
     * Compare if the two element values are equal
     * @param e1 the first element to compare
     * @param e2 the second element to compare
     * @return true if both element values are equal
     */
    boolean equals(Element e1, Element e2);

}
