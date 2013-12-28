package com.vteba.cache.memcached.store;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vteba.cache.hibernate.CacheException;
import com.vteba.cache.hibernate.memcached.Memcache;
import com.vteba.cache.hibernate.memcached.concurrent.CacheLockProvider;
import com.vteba.cache.hibernate.memcached.concurrent.ReadWriteLockSync;
import com.vteba.cache.hibernate.memcached.concurrent.Sync;
import com.vteba.cache.memcached.Element;
import com.vteba.cache.memcached.MemcacheClientDelegate;
import com.vteba.cache.memcached.Status;
import com.vteba.cache.memcached.pool.Pool;
import com.vteba.cache.memcached.pool.PoolAccessor;
import com.vteba.cache.memcached.pool.PoolableStore;
import com.vteba.cache.memcached.pool.Size;
import com.vteba.cache.memcached.pool.impl.DefaultSizeOfEngine;
import com.vteba.cache.memcached.store.chm.SelectableConcurrentHashMap;
import com.vteba.cache.memcached.util.ratestatistics.AtomicRateStatistic;
import com.vteba.cache.memcached.util.ratestatistics.RateStatistic;

/**
 * A Store implementation suitable for fast, concurrent in memory stores. The policy is determined by that
 * configured in the cache.
 *
 * @author <a href="mailto:ssuravarapu@users.sourceforge.net">Surya Suravarapu</a>
 * @version $Id: MemoryStore.java 5799 2012-06-15 20:30:56Z twu $
 */
public class MemoryStore implements PoolableStore {

    /**
     * This is the default from {@link java.util.concurrent.ConcurrentHashMap}. It should never be used, because we size
     * the map to the max size of the store.
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * Set optimisation for 100 concurrent threads.
     */
    private static final int CONCURRENCY_LEVEL = 100;

    private static final int MAX_EVICTION_RATIO = 5;

    private static final Logger LOG = LoggerFactory.getLogger(MemoryStore.class.getName());

    private final boolean alwaysPutOnHeap;

    /**
     * The cache this store is associated with.
     */
    private Memcache memcache;

    /**
     * Map where items are stored by key.
     */
    private final SelectableConcurrentHashMap map;
    private final PoolAccessor<PoolableStore> poolAccessor;

    private final RateStatistic hitRate = new AtomicRateStatistic(1000, TimeUnit.MILLISECONDS);
    private final RateStatistic missRate = new AtomicRateStatistic(1000, TimeUnit.MILLISECONDS);

    private final boolean storePinned;
    private final boolean elementPinningEnabled;

    /**
     * The maximum size of the store (0 == no limit)
     */
    private volatile int maximumSize;

    /**
     * status.
     */
    private volatile Status status;

    /**
     * The eviction policy to use
     */
    private volatile Policy policy;

    /**
     * The pool accessor
     */

    private volatile CacheLockProvider lockProvider;

    /**
     * Constructs things that all MemoryStores have in common.
     *
     * @param cache the cache
     * @param pool the pool tracking the on-heap usage
     * @param notify whether to notify the Cache's EventNotificationService on eviction and expiry
     */
    protected MemoryStore(MemoryStoreEvictionPolicy policy, Pool<PoolableStore> pool, boolean notify, BackingFactory factory) {
        status = Status.STATUS_UNINITIALISED;
        //this.cache = cache;
        this.maximumSize = 20000;//(int) cache.getCacheConfiguration().getMaxEntriesLocalHeap();
        this.policy = determineEvictionPolicy(policy);

        this.poolAccessor = pool.createPoolAccessor(this, 6, true);
        this.alwaysPutOnHeap = false;//getAdvancedBooleanConfigProperty("alwaysPutOnHeap", cache.getCacheConfiguration().getName(), false);
        this.storePinned = false;//determineStorePinned(cache.getCacheConfiguration());

        this.elementPinningEnabled = false;//!cache.getCacheConfiguration().isOverflowToOffHeap();

        // create the CHM with initialCapacity sufficient to hold maximumSize
        final float loadFactor = maximumSize == 1 ? 1 : DEFAULT_LOAD_FACTOR;
        int initialCapacity = getInitialCapacityForLoadFactor(maximumSize, loadFactor);
        int maximumCapacity = isClockEviction() && !storePinned ? maximumSize : 0;
        //RegisteredEventListeners eventListener = notify ? cache.getCacheEventNotificationService() : null;
        this.map = factory.newBackingMap(poolAccessor, elementPinningEnabled, initialCapacity,
                loadFactor, CONCURRENCY_LEVEL, maximumCapacity);//, eventListener);

        this.status = Status.STATUS_ALIVE;

//        if (LOG.isDebugEnabled()) {
//            //LOG.debug("Initialized " + this.getClass().getName() + " for " + cache.getName());
//        }
    }

    /**
     * Calculates the initialCapacity for a desired maximumSize goal and loadFactor.
     *
     * @param maximumSizeGoal the desired maximum size goal
     * @param loadFactor      the load factor
     * @return the calculated initialCapacity. Returns 0 if the parameter <tt>maximumSizeGoal</tt> is less than or equal
     *         to 0
     */
    protected static int getInitialCapacityForLoadFactor(int maximumSizeGoal, float loadFactor) {
        double actualMaximum = Math.ceil(maximumSizeGoal / loadFactor);
        return Math.max(0, actualMaximum >= Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) actualMaximum);
    }

    /**
     * A factory method to create a MemoryStore.
     *
     * @param policy the cache evict policy
     * @param pool the pool tracking the on-heap usage
     * @return an instance of a MemoryStore, configured with the appropriate eviction policy
     */
    public static MemoryStore create(MemoryStoreEvictionPolicy policy, Pool<PoolableStore> pool) {
        MemoryStore memoryStore = new MemoryStore(policy, pool, false, new BasicBackingFactory());
        ///cache.getCacheConfiguration().addConfigurationListener(memoryStore);
        return memoryStore;
    }

    /**
     * {@inheritDoc}
     */
    public void unpinAll() {
        if (elementPinningEnabled) {
            map.unpinAll();
        }
    }

    /**
     * {@inheritDoc}
     */
    public void setPinned(Object key, boolean pinned) {
        if (elementPinningEnabled) {
            map.setPinned(key, pinned);
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean isPinned(Object key) {
        return elementPinningEnabled && map.isPinned(key);
    }

    private boolean isPinningEnabled(Element element) {
        return storePinned || isPinned(element.getObjectKey());
    }

    /**
     * {@inheritDoc}
     */
    public void fill(Element element) {
        if (alwaysPutOnHeap || isPinningEnabled(element) || remove(element.getObjectKey()) != null || canPutWithoutEvicting(element)) {
            put(element);
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean removeIfNotPinned(final String key) {
        return !storePinned && !isPinned(key) && remove(key) != null;
    }

    /**
     * Puts an item in the store. Note that this automatically results in an eviction if the store is full.
     *
     * @param element the element to add
     */
    public boolean put(final Element element) throws CacheException {
        if (element == null) {
            return false;
        }

        long delta = poolAccessor.add(element.getObjectKey(), element.getObjectValue(), map.storedObject(element), isPinningEnabled(element));
        if (delta > -1) {
            Element old = map.put(element.getObjectKey(), element, delta);
            checkCapacity(element);
            return old == null;
        } else {
            notifyDirectEviction(element);
            return true;
        }
    }

    /**
     * Gets an item from the cache.
     * <p/>
     * The last access time in {@link net.sf.ehcache.Element} is updated.
     *
     * @param key the key of the Element
     * @return the element, or null if there was no match for the key
     */
    public final Element get(final String key) {
        if (key == null) {
            return null;
        } else {
            final Element e = map.get(key);
            if (e == null) {
                missRate.event();
            } else {
                hitRate.event();
            }
            return e;
        }
    }

    /**
     * Removes an Element from the store.
     *
     * @param key the key of the Element, usually a String
     * @return the Element if one was found, else null
     */
    public Element remove(final String key) {
        if (key == null) {
            return null;
        }
        return map.remove(key);
    }

    /**
     * {@inheritDoc}
     */
    public boolean isTierPinned() {
        return storePinned;
    }

    /**
     * {@inheritDoc}
     */
    public Set<?> getPresentPinnedKeys() {
        return map.pinnedKeySet();
    }

    /**
     * Expire all elements.
     * <p/>
     * This is a default implementation which does nothing. Expiration on demand is only implemented for disk stores.
     */
    public void expireElements() {
        for (Object key : map.keySet()) {
            expireElement(key);
        }
    }

    /**
     * Evicts the element for the given key, if it exists and is expired
     * @param key the key
     * @return the evicted element, if any. Otherwise null
     */
    protected Element expireElement(final Object key) {
        Element value = get(key.toString());
        //return value != null && value.isExpired() && map.remove(key, value) ? value : null;
        return value != null && map.remove(key, value) ? value : null;
    }

    /**
     * Chooses the Policy from the cache configuration
     * @param cache the cache
     * @return the chosen eviction policy
     */
    private static Policy determineEvictionPolicy(MemoryStoreEvictionPolicy evictionPolicy) {
    	MemoryStoreEvictionPolicy policySelection = MemoryStoreEvictionPolicy.LRU;//cache.getCacheConfiguration().getMemoryStoreEvictionPolicy();
    	if (evictionPolicy != null) {
    		policySelection = evictionPolicy;
    	}

        if (policySelection.equals(MemoryStoreEvictionPolicy.LRU)) {
            return new LruPolicy();
        } else if (policySelection.equals(MemoryStoreEvictionPolicy.FIFO)) {
            return new FifoPolicy();
        } else if (policySelection.equals(MemoryStoreEvictionPolicy.LFU)) {
            return new LfuPolicy();
        } else if (policySelection.equals(MemoryStoreEvictionPolicy.CLOCK)) {
            return null;
        }

        throw new IllegalArgumentException(policySelection + " isn't a valid eviction policy");
    }

    /**
     * Remove all of the elements from the store.
     */
    public final void removeAll() throws CacheException {
        for (Object key : map.keySet()) {
            remove(key.toString());
        }
    }

    /**
     * Prepares for shutdown.
     */
    public synchronized void dispose() {
        if (status.equals(Status.STATUS_SHUTDOWN)) {
            return;
        }
        status = Status.STATUS_SHUTDOWN;
        poolAccessor.unlink();
    }

    /**
     * Gets an Array of the keys for all elements in the memory cache.
     * <p/>
     * Does not check for expired entries
     *
     * @return An List
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public final List getKeys() {
        return new ArrayList<Object>(keySet());
    }

    /**
     * Returns the keySet for this store
     * @return keySet
     */
    protected Set<?> keySet() {
        return map.keySet();
    }

    /**
     * Returns the current store size.
     *
     * @return The size value
     */
    public final int getSize() {
        return map.size();
    }

    /**
     * A check to see if a key is in the Store. No check is made to see if the Element is expired.
     *
     * @param key The Element key
     * @return true if found. If this method return false, it means that an Element with the given key is definitely not
     *         in the MemoryStore. If it returns true, there is an Element there. An attempt to get it may return null if
     *         the Element has expired.
     */
    @Override
    public final boolean containsKey(final String key) {
        return map.containsKey(key);
    }

    /**
     * Before eviction elements are checked.
     *
     * @param element the element to notify about its expiry
     */
    private void notifyExpiry(final Element element) {
        //cache.getCacheEventNotificationService().notifyElementExpiry(element, false);
    }

    /**
     * Called when an element is evicted even before it could be installed inside the store
     *
     * @param element the evicted element
     */
    protected void notifyDirectEviction(final Element element) {
    }

    /**
     * An algorithm to tell if the MemoryStore is at or beyond its carrying capacity.
     * 这个算法告诉我们，是否MemoryStore超出或者即将超出它所能携带的容量
     * @return true if the store is full, false otherwise
     */
    public final boolean isFull() {
        return maximumSize > 0 && map.quickSize() >= maximumSize;
    }

    /**
     * Check if adding an element won't provoke an eviction.
     * 检查是否添加一个元素不会到导致移除发生
     * @param element the element
     * @return true if the element can be added without provoking an eviction.
     */
    public final boolean canPutWithoutEvicting(Element element) {
        if (element == null) {
            return true;
        }

        return !isFull() && poolAccessor.canAddWithoutEvicting(element.getObjectKey(), element.getObjectValue(), map.storedObject(element));
    }

    /**
     * If the store is over capacity, evict elements until capacity is reached
     * 如果存储超出容量了，移除元素，直到低于容量
     * @param elementJustAdded the element added by the action calling this check
     */
    private void checkCapacity(final Element elementJustAdded) {
        if (maximumSize > 0 && !isClockEviction()) {
            int evict = Math.min(map.quickSize() - maximumSize, MAX_EVICTION_RATIO);
            for (int i = 0; i < evict; i++) {
                removeElementChosenByEvictionPolicy(elementJustAdded);
            }
        }
    }

    /**
     * Removes the element chosen by the eviction policy
     * 根据移除策略，删除元素
     * @param elementJustAdded it is possible for this to be null
     * @return true if an element was removed, false otherwise.
     */
    private boolean removeElementChosenByEvictionPolicy(final Element elementJustAdded) {

        if (policy == null) {
            return map.evict();//随机删除
        }

        Element element = findEvictionCandidate(elementJustAdded);
        if (element == null) {
            LOG.debug("Eviction selection miss. Selected element is null");
            return false;
        }

        // If the element is expired, remove
        if (element.isExpired()) {
            remove(element.getObjectKey());
            notifyExpiry(element);
            return true;
        }

        if (isPinningEnabled(element)) {
            return false;
        }

        return evict(element);
    }

    /**
     * Find a "relatively" unused element.
     * 寻找一个“相对”不使用的元素
     * @param elementJustAdded the element added by the action calling this check
     * @return the element chosen as candidate for eviction
     */
    private Element findEvictionCandidate(final Element elementJustAdded) {
        Object objectKey = elementJustAdded != null ? elementJustAdded.getObjectKey() : null;
        Element[] elements = sampleElements(objectKey);
        // this can return null. Let the cache get bigger by one.
        return policy.selectedBasedOnPolicy(elements, elementJustAdded);
    }

    /**
     * Uses random numbers to sample the entire map.
     * <p/>
     * This implemenation uses a key array.
     *
     * @param keyHint a key used as a hint indicating where the just added element is
     * @return a random sample of elements
     */
    private Element[] sampleElements(Object keyHint) {
        int size = AbstractPolicy.calculateSampleSize(map.quickSize());
        return map.getRandomValues(size, keyHint);
    }

    /**
     * {@inheritDoc}
     */
    public Object getInternalContext() {
        if (lockProvider != null) {
            return lockProvider;
        } else {
            lockProvider = new LockProvider();
            return lockProvider;
        }
    }

    /**
     * Gets the status of the MemoryStore.
     */
    public final Status getStatus() {
        return status;
    }

    /**
     * {@inheritDoc}
     */
    public void memoryCapacityChanged(int oldCapacity, int newCapacity) {
        maximumSize = newCapacity;
        if (isClockEviction() && !storePinned) {
            map.setMaxSize(maximumSize);
        }
    }

    private boolean isClockEviction() {
        return policy == null;
    }

    /**
     * {@inheritDoc}
     */
    public boolean containsKeyInMemory(String key) {
        return containsKey(key);
    }

    /**
     * {@inheritDoc}
     */
    public boolean containsKeyOffHeap(Object key) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public Policy getInMemoryEvictionPolicy() {
        return policy;
    }

    /**
     * {@inheritDoc}
     */
    public int getInMemorySize() {
        return getSize();
    }

    /**
     * {@inheritDoc}
     */
    public long getInMemorySizeInBytes() {
        if (poolAccessor.getSize() < 0) {
            DefaultSizeOfEngine defaultSizeOfEngine = new DefaultSizeOfEngine(6, true);
            long sizeInBytes = 0;
            for (Object o : map.values()) {
                Element element = (Element)o;
                if (element != null) {
                    Size size = defaultSizeOfEngine.sizeOf(element.getObjectKey(), element, map.storedObject(element));
                    sizeInBytes += size.getCalculated();
                }
            }
            return sizeInBytes;
        }
        return poolAccessor.getSize();
    }

    /**
     * {@inheritDoc}
     */
    public int getOffHeapSize() {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    public boolean hasAbortedSizeOf() {
        return poolAccessor.hasAbortedSizeOf();
    }

    /**
     * {@inheritDoc}
     */
    public void setInMemoryEvictionPolicy(Policy policy) {
        this.policy = policy;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Element putIfAbsent(Element element) throws NullPointerException {
        if (element == null) {
            return null;
        }

        long delta = poolAccessor.add(element.getObjectKey(), element.getObjectValue(), map.storedObject(element), isPinningEnabled(element));
        if (delta > -1) {
            Element old = map.putIfAbsent(element.getObjectKey(), element, delta);
            if (old == null) {
              checkCapacity(element);
            } else {
              poolAccessor.delete(delta);
            }
            return old;
        } else {
            notifyDirectEviction(element);
            return null;
        }
    }

    /**
     * Evicts the element from the store
     * @param element the element to be evicted
     * @return true if succeeded, false otherwise
     */
    protected boolean evict(final Element element) {
        final Element remove = remove(element.getObjectKey());
//        RegisteredEventListeners cacheEventNotificationService = cache.getCacheEventNotificationService();
//        final FrontEndCacheTier frontEndCacheTier = cacheEventNotificationService.getFrontEndCacheTier();
//        if (remove != null && frontEndCacheTier != null && frontEndCacheTier.notifyEvictionFromCache(remove.getKey())) {
//            cacheEventNotificationService.notifyElementEvicted(remove, false);
//        }
        return remove != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Element removeElement(Element element, ElementValueComparator comparator) throws NullPointerException {
        if (element == null || element.getObjectKey() == null) {
            return null;
        }

        Object key = element.getObjectKey();

        Lock lock = getWriteLock(key);
        lock.lock();
        try {
            Element toRemove = map.get(key);
            if (comparator.equals(element, toRemove)) {
                map.remove(key);
                return toRemove;
            } else {
                return null;
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean replace(Element old, Element element, ElementValueComparator comparator) throws NullPointerException,
            IllegalArgumentException {
        if (element == null || element.getObjectKey() == null) {
            return false;
        }

        Object key = element.getObjectKey();

        long delta = poolAccessor.add(element.getObjectKey(), element.getObjectValue(), map.storedObject(element), isPinningEnabled(element));
        if (delta > -1) {
            Lock lock = getWriteLock(key);
            lock.lock();
            try {
                Element toRemove = map.get(key);
                if (comparator.equals(old, toRemove)) {
                    map.put(key, element, delta);
                    return true;
                } else {
                    poolAccessor.delete(delta);
                    return false;
                }
            } finally {
                lock.unlock();
            }
        } else {
            notifyDirectEviction(element);
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    public Element replace(Element element) throws NullPointerException {
        if (element == null || element.getObjectKey() == null) {
            return null;
        }

        Object key = element.getObjectKey();

        long delta = poolAccessor.add(element.getObjectKey(), element.getObjectValue(), map.storedObject(element), isPinningEnabled(element));
        if (delta > -1) {
            Lock lock = getWriteLock(key);
            lock.lock();
            try {
                Element toRemove = map.get(key);
                if (toRemove != null) {
                    map.put(key, element, delta);
                    return toRemove;
                } else {
                    poolAccessor.delete(delta);
                    return null;
                }
            } finally {
                lock.unlock();
            }
        } else {
            notifyDirectEviction(element);
            return null;
        }
    }

    /**
     * {@inheritDoc}执行移除操作，释放堆空间
     */
    public boolean evictFromOnHeap(int count, long size) {
        if (storePinned) {
            return false;
        }

        for (int i = 0; i < count; i++) {
            boolean removed = removeElementChosenByEvictionPolicy(null);
            if (!removed) {
                return false;
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}获得堆的命中率
     */
    public float getApproximateHeapHitRate() {
        return hitRate.getRate();
    }

    /**
     * {@inheritDoc}获得堆的命中失效率
     */
    public float getApproximateHeapMissRate() {
        return missRate.getRate();
    }

    /**
     * {@inheritDoc}获得堆的近似大小
     */
    public long getApproximateHeapCountSize() {
        return map.quickSize();
    }

    /**
     * {@inheritDoc}
     * 获得堆的近似字节大小。
     */
    public long getApproximateHeapByteSize() {
        return poolAccessor.getSize();
    }

    /**
     * 获得key的写锁定
     * @param key
     * @return Lock写锁
     * @author yinlei
     * date 2012-12-15 下午9:44:00
     */
    private Lock getWriteLock(Object key) {
        return map.lockFor(key).writeLock();
    }

    /**
     * Get a collection of the elements in this store
     * 获得元素在存储介质中的集合
     * @return element collection
     */
    public Collection<Element> elementSet() {
        return map.values();
    }

    /**
     * LockProvider implementation that uses the segment locks.
     * 片段锁定的实现
     */
    private class LockProvider implements CacheLockProvider {

        /**
         * {@inheritDoc}
         */
        public Sync getSyncForKey(Object key) {
            return new ReadWriteLockSync(map.lockFor(key));
        }
    }

    /**
     * 计算key对应的元素的大小
     * @param key
     * @author yinlei
     * date 2012-12-15 下午9:42:37
     */
    public void recalculateSize(Object key) {
        if (key == null) {
            return;
        }
        map.recalculateSize(key);
    }

    /**
     * Factory interface to create a MemoryStore backing.创建内存存储备份的工厂接口
     */
    protected interface BackingFactory {
        /**
         * Create a MemoryStore backing map.
         * 创建一个内存存储的备份map
         * @param poolAccessor on-heap pool accessor
         * @param elementPinning element pinning in this store
         * @param initialCapacity initial store capacity
         * @param loadFactor map load factor
         * @param concurrency map concurrency
         * @param maximumCapacity maximum store capacity
         * @param eventListener event listener (or {@code null} for no notifications)
         * @return a backing map
         */
        SelectableConcurrentHashMap newBackingMap(PoolAccessor<PoolableStore> poolAccessor, boolean elementPinning, int initialCapacity,
                float loadFactor, int concurrency, int maximumCapacity);
    }

    /**
     * Simple backing map factory.简单的map工厂备份
     */
    static class BasicBackingFactory implements BackingFactory {

        @Override
        public SelectableConcurrentHashMap newBackingMap(PoolAccessor<PoolableStore> poolAccessor, boolean elementPinning, int initialCapacity,
                float loadFactor, int concurrency, int maximumCapacity) {
            return new SelectableConcurrentHashMap(poolAccessor, elementPinning, initialCapacity,
                    loadFactor, concurrency, maximumCapacity);
        }
    }

	@Override
	public void putAll(Collection<Element> elements) throws CacheException {
		for (Element element : elements) {
			put(element);
		}
	}

	@Override
	public void removeAll(Collection<String> keys) {
		for (String key : keys) {
			remove(key);
		}
	}

	@Override
	public Map<String, Element> getAll(Collection<String> keys) {
		Map<String, Element> map = new HashMap<String, Element>();
		for (String key : keys) {
			Element element = get(key);
			map.put(key, element);
		}
		return map;
	}

	@Override
	public MemcacheClientDelegate getMemcacheClientDelegate() {
		return null;
	}

	@Override
	public Memcache getMemcache() {
		return memcache;
	}

	@Override
	public void setMemcache(Memcache memcache) {
		this.memcache = memcache;
	}
}

