package com.vteba.cache.memcached.transaction.xa.commands;

import com.vteba.cache.memcached.Element;

/**
 * Represents a {@link net.sf.ehcache.store.Store#remove(Object) remove} operation to be executed on a {@link net.sf.ehcache.store.Store}.
 *
 * @author Alex Snaps
 */
public class StoreRemoveCommand extends AbstractStoreCommand {

    private Object key;

    /**
     * Create a StoreRemoveCommand
     *
     * @param key the key of the element to remove
     * @param oldElement the element in the underlying store at the time this command is created
     */
    public StoreRemoveCommand(final Object key, final Element oldElement) {
        super(oldElement, null);
        this.key = key;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isPut(Object key) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isRemove(Object key) {
        return getObjectKey().equals(key);
    }


    /**
     * {@inheritDoc}
     */
    public String getObjectKey() {
        return key.toString();
    }

    /**
     * Getter to the cache entry to be removed
     *
     * @return the cache entry
     */
    public Element getEntry() {
        //return new CacheEntry(key, getOldElement());
    	return getOldElement();
    }
}
