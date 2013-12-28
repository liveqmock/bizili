package com.vteba.cache.memcached.store;

import java.util.Arrays;

import com.vteba.cache.memcached.Element;
import com.vteba.cache.memcached.store.compound.ReadWriteCopyStrategy;
import com.vteba.cache.memcached.store.compound.ReadWriteSerializationCopyStrategy;

/**
 * @author Ludovic Orban
 */
public class DefaultElementValueComparator implements ElementValueComparator {

    //private final CacheConfiguration cacheConfiguration;

    /**
     * Constructor
     *
     * @param cacheConfiguration the cache configuration
     */
    public DefaultElementValueComparator() {
        //this.cacheConfiguration = cacheConfiguration;
    }

    /**
     * {@inheritDoc}
     */
    public boolean equals(Element e1, Element e2) {
        if (e1 == null && e2 == null) {
            return true;
        } else if (e1 != null && e1.equals(e2)) {
            if (e1.getObjectValue() == null) {
                return e2.getObjectValue() == null;
            } else {
                return compareValues(copyForReadIfNeeded(e1).getObjectValue(), copyForReadIfNeeded(e2).getObjectValue());
            }
        } else {
            return false;
        }
    }

    private static boolean compareValues(Object objectValue1, Object objectValue2) {
        if (objectValue1 != null && objectValue2 != null && objectValue1.getClass().isArray() && objectValue2.getClass().isArray()) {
            return Arrays.deepEquals(new Object[] {objectValue1}, new Object[] {objectValue2});
        } else {
            if (objectValue1 == null) {
                return objectValue2 == null;
            } else {
                return objectValue1.equals(objectValue2);
            }
        }
    }

    private Element copyForReadIfNeeded(Element element) {
        ReadWriteCopyStrategy<Element> readWriteCopyStrategy = new ReadWriteSerializationCopyStrategy();//cacheConfiguration.getCopyStrategy();
        if (readWriteCopyStrategy == null || skipCopyForRead()) {
            return element;
        }
        return readWriteCopyStrategy.copyForRead(element);
    }

    private boolean skipCopyForRead() {
        // only do a copy for read before comparing if both copy on read and copy on write are enabled
        //return !(cacheConfiguration.isCopyOnRead() && cacheConfiguration.isCopyOnWrite());
    	return false;
    }

}
