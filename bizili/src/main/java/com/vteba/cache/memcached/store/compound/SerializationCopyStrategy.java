package com.vteba.cache.memcached.store.compound;

import com.vteba.cache.memcached.Element;

/**
 * A copy strategy that uses full Serialization to copy the object graph
 *
 * @author Alex Snaps
 * @author Ludovic Orban
 */
public class SerializationCopyStrategy implements ReadWriteCopyStrategy<Element> {

	private static final long serialVersionUID = 939556085986035849L;
	private final ReadWriteSerializationCopyStrategy copyStrategy = new ReadWriteSerializationCopyStrategy();

    /**
     * @inheritDoc
     */
    public Element copyForWrite(Element value) {
        return copyStrategy.copyForRead(copyStrategy.copyForWrite(value));
    }

    /**
     * @inheritDoc
     */
    public Element copyForRead(Element storedValue) {
        return copyStrategy.copyForRead(copyStrategy.copyForWrite(storedValue));
    }
}
