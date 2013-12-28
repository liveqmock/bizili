package com.vteba.cache.memcached.store.compound;

import com.vteba.cache.memcached.Element;

/**
 * @author Alex Snaps
 * @author Ludovic Orban
 */
public class ImmutableValueElementCopyStrategy implements ReadWriteCopyStrategy<Element> {

	private static final long serialVersionUID = 5424665675202262601L;
	private final ReadWriteSerializationCopyStrategy copyStrategy = new ReadWriteSerializationCopyStrategy();

    /**
     * @inheritDoc
     */
    public Element copyForWrite(Element value) {
        if (value == null) {
            return null;
        }
        return copyStrategy.duplicateElementWithNewValue(value, value.getObjectValue());
    }

    /**
     * @inheritDoc
     */
    public Element copyForRead(Element storedValue) {
        if (storedValue == null) {
            return null;
        }
        return copyStrategy.duplicateElementWithNewValue(storedValue, storedValue.getObjectValue());
    }
}
