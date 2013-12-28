package com.vteba.cache.memcached.transaction;

/**
 * A factory of read-committed soft locks.
 *
 * @author Chris Dennis
 */
public class ReadCommittedSoftLockFactory implements SoftLockFactory {

    /**
     * {@inheritDoc}
     */
    @Override
    public ReadCommittedSoftLockImpl newSoftLock(SoftLockManager manager, Object key) {
        return new ReadCommittedSoftLockImpl(manager, key);
    }

}
