package com.vteba.cache.memcached.transaction;

import com.vteba.cache.hibernate.memcached.Memcache;
import com.vteba.cache.memcached.Element;
import com.vteba.cache.memcached.Store;
import com.vteba.cache.memcached.store.compound.ReadWriteCopyStrategy;

/**
 * Abstract transactional store which provides implementation of all non-transactional methods
 *
 * @author Ludovic Orban
 */
public abstract class AbstractTransactionStore implements Store {
	
	/**
     * The memory store in this store
     */
    protected final Store memoryStore;
    
    /**
     * The underlying store wrapped by this store
     */
    protected final Store memcacheStore;
    
    protected Memcache memcache;
    
    /**
     * The copy strategy for this store
     */
    protected final ReadWriteCopyStrategy<Element> copyStrategy;

    /**
     * Constructor
     * @param memoryStore 同一个事务内的本地内存存储
     * @param memcacheStore 基于memcache的底层的持久化存储
     * @param copyStrategy 元素读写复制策略
     */
    protected AbstractTransactionStore(Store memoryStore, Store memcacheStore, ReadWriteCopyStrategy<Element> copyStrategy) {
        this.memoryStore = memoryStore;
        this.memcacheStore = memcacheStore;
        this.copyStrategy = copyStrategy;
    }

    /**
     * {@inheritDoc}
     */
    public void expireElements() {
        memoryStore.expireElements();
    }

    /**
     * Copy element for read operation
     *
     * @param element
     * @return copied element
     */
    protected Element copyElementForRead(Element element) {
        return copyStrategy.copyForRead(element);
    }

    /**
     * Copy element for write operation
     *
     * @param element
     * @return copied element
     */
    protected Element copyElementForWrite(Element element) {
        return copyStrategy.copyForWrite(element);
    }

	public Memcache getMemcache() {
		return memcache;
	}

	public void setMemcache(Memcache memcache) {
		this.memcache = memcache;
	}
    
}
