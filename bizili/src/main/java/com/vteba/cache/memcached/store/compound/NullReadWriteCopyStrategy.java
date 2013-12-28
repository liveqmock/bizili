package com.vteba.cache.memcached.store.compound;

import com.vteba.cache.memcached.Element;

/**
 * No-op copy strategy
 *
 * @author teck
 */
public class NullReadWriteCopyStrategy implements ReadWriteCopyStrategy<Element> {

	private static final long serialVersionUID = 8401445344199068975L;

	/**
     * {@inheritDoc}
     */
    public Element copyForWrite(Element value) {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    public Element copyForRead(Element storedValue) {
        return storedValue;
    }
}
