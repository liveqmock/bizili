package com.vteba.cache.memcached.transaction.xa.commands;

import com.vteba.cache.memcached.Element;

/**
 * Represents a {@link net.sf.ehcache.store.Store#put(net.sf.ehcache.Element)} put} operation to be executed on a {@link net.sf.ehcache.store.Store}.
 * @author Alex Snaps
 */
public class StorePutCommand extends AbstractStoreCommand {

    /**
     * Create a StorePutCommand
     *
     * @param oldElement the element in the underlying store at the time this command is created
     * @param newElement the new element to put in the underlying store
     */
    public StorePutCommand(final Element oldElement, final Element newElement) {
        super(oldElement, newElement);
    }

    /**
     * {@inheritDoc}
     */
    public boolean isPut(Object key) {
        return getObjectKey().equals(key);
    }

    /**
     * {@inheritDoc}
     */
    public boolean isRemove(Object key) {
        return false;
    }


    /**
     * Getter to the Element instance to be put in the Store
     *
     * @return the element instance
     */
    public Element getElement() {
        return getNewElement();
    }

    /**
     * {@inheritDoc}
     */
    public String getObjectKey() {
        return getNewElement().getKey();
    }


}
