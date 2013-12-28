package com.vteba.cache.memcached.transaction.xa.commands;

import com.vteba.cache.memcached.Element;
import com.vteba.cache.memcached.Store;
import com.vteba.cache.memcached.store.ElementValueComparator;
import com.vteba.cache.memcached.transaction.SoftLock;
import com.vteba.cache.memcached.transaction.SoftLockID;
import com.vteba.cache.memcached.transaction.SoftLockManager;
import com.vteba.cache.memcached.transaction.xa.OptimisticLockFailureException;
import com.vteba.cache.memcached.transaction.xa.XidTransactionID;

/**
 * @author Ludovic Orban
 */
public abstract class AbstractStoreCommand implements Command {

    private final Element oldElement;
    private final Element newElement;

    private Element softLockedElement;

    /**
     * Create a Store Command
     * @param oldElement the element in the underlying store at the time this command is created
     * @param newElement the new element to put in the underlying store
     */
    public AbstractStoreCommand(final Element oldElement, final Element newElement) {
        this.newElement = newElement;
        this.oldElement = oldElement;
    }

    /**
     * Get the element in the underlying store at the time this command is created
     * @return the old element
     */
    protected Element getOldElement() {
        return oldElement;
    }

    /**
     * Get the new element to put in the underlying store
     * @return the new element to put in the underlying store
     */
    protected Element getNewElement() {
        return newElement;
    }

    /**
     * {@inheritDoc}
     */
    public boolean prepare(Store store, SoftLockManager softLockManager, XidTransactionID transactionId, ElementValueComparator comparator) {
        Object objectKey = getObjectKey();
        final boolean wasPinned = false;///store.isPinned(objectKey);

        SoftLockID softLockId = softLockManager.createSoftLockID(transactionId, objectKey, newElement, oldElement, wasPinned);
        SoftLock softLock = softLockManager.findSoftLockById(softLockId);
        softLockedElement = createElement(objectKey, softLockId, store, wasPinned);
        softLock.lock();
        softLock.freeze();

        if (oldElement == null) {
            Element previousElement = store.putIfAbsent(softLockedElement);
            if (previousElement != null) {
                softLock.unfreeze();
                softLock.unlock();
                softLockedElement = null;
                throw new OptimisticLockFailureException();
            }
        } else {
            boolean replaced = store.replace(oldElement, softLockedElement, comparator);
            if (!replaced) {
                softLock.unfreeze();
                softLock.unlock();
                softLockedElement = null;
                throw new OptimisticLockFailureException();
            }
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    public void rollback(Store store, SoftLockManager softLockManager) {
        if (oldElement == null) {
            store.remove(getObjectKey());
        } else {
            store.put(oldElement);
        }

        SoftLockID softLockId = (SoftLockID) softLockedElement.getObjectValue();
        SoftLock softLock = softLockManager.findSoftLockById(softLockId);

//        if (!softLockId.wasPinned()) {
//            store.setPinned(softLockId.getKey(), false);
//        }

        softLock.unfreeze();
        softLock.unlock();
        softLockedElement = null;
    }

    private Element createElement(Object key, SoftLockID softLockId, Store store, boolean wasPinned) {
        Element element = new Element(key.toString(), softLockId);
        element.setEternal(true);
//        if (!wasPinned) {
//            store.setPinned(key, true);
//        }
        return element;
    }

}
