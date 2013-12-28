package com.vteba.cache.memcached;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.vteba.cache.hibernate.CacheException;
import com.vteba.cache.hibernate.memcached.Memcache;
import com.vteba.cache.memcached.store.ElementValueComparator;

/**
 * 缓存存储实例接口，定义操作规范
 * @author yinlei
 * date 2012-11-15 下午8:02:13
 */
public interface Store {

    /**
     * Puts an item into the store.将元素放入缓存。
     * @return 如果是新放入或者元素师null，返回true。如果它是一个更新，返回false.
     */
	public boolean put(Element element) throws CacheException;

    /**
     * Puts a collection of elements into the store.将整个集合的元素存入缓存
     * @param elements Collection of elements to be put in the store
     */
    public void putAll(Collection<Element> elements) throws CacheException;

    /**
     * Gets an item from the cache.
     * 从缓存中获取一个元素
     * @param key 元素key
     */
    public Element get(String key);

    /**
     * Gets an Array of the keys for all elements in the disk store.
     * 获得该缓存中所有的元素的key的list
     * @return An List of {@link java.io.Serializable} keys
     */
    public List<String> getKeys();

    /**
     * Removes an item from the cache.
     * 从缓存中删除一个元素
     * @param key 元素key
     */
    public Element remove(String key);

    /**
     * Removes a collection of elements from the cache.
     * 从存储中删除集合中所有key的元素
     */
    public void removeAll(Collection<String> keys);

    /**
     * Put an element in the store if no element is currently mapped to the elements key.
     * 如果该元素的key还没有对应的value，将元素放入存储
     * @param element element to be added
     * @return the element previously cached for this key, or null if none.
     * 以前以该key为键的元素，没有返回null
     * @throws NullPointerException if the element is null, or has a null key
     */
    public Element putIfAbsent(Element element) throws NullPointerException;

    /**
     * Remove the Element mapped to the key for the supplied element if the value of the supplied Element
     * is equal to the value of the cached Element. This is a CAS operation. It is consistent even against
     * a distributed cache that is not coherent. If the old value is stale when this operation is attempted
     * the remove does not take place.
     * 元素值都相等了，还有必要去更新吗？
     * @param element Element to be removed
     * @param comparator ElementValueComparator to use to compare elements
     * @return the Element removed or null if no Element was removed
     *
     * @throws NullPointerException if the element is null, or has a null key
     */
    public Element removeElement(Element element, ElementValueComparator comparator) throws NullPointerException;//ElementValueComparator comparator

    /**
     * Replace the cached element only if the value of the current Element is equal to the value of the
     * supplied old Element.
     * 元素值都相等了，还有必要去更新吗？
     * @param old Element to be test against
     * @param element Element to be cached
     * @param comparator ElementValueComparator to use to compare elements
     * @return true is the Element was replaced
     * @throws NullPointerException if the either Element is null or has a null key
     * @throws IllegalArgumentException if the two Element keys are non-null but not equal
     */
    public boolean replace(Element old, Element element, ElementValueComparator comparator) throws NullPointerException, IllegalArgumentException;

    /**
     * A check to see if a key is in the Store.
     * 检查key是否在存储中
     * @param key The Element key
     * @return true if found. No check is made to see if the Element is expired.
     *  1.2
     */
    public boolean containsKey(String key);

    /**
     * Expire all elements.过期所有元素
     */
    public void expireElements();

    /**
     * Retries the elements associated with a set of keys and update the statistics
     * Keys which are not present in the cache will have null values associated
     * with them in the returned map
     *
     * @param keys a collection of keys to look for
     * @return a map of keys and their corresponding values
     */
    public Map<String, Element> getAll(Collection<String> keys);
    
    /**
     * 获得客户端委托代理实例
     * @return MemcacheClientDelegate
     * @author yinlei
     * date 2013-4-4 下午4:22:14
     */
    public MemcacheClientDelegate getMemcacheClientDelegate();
    
    /**
     * 获得该存储实例所属的Memcache。和Store是互相引用
     * @return 当前存储Store所属的Memcache实例
     * @author yinlei
     * date 2013-4-5 下午1:52:35
     */
    public Memcache getMemcache();
    
    /**
     * 设置该存储所属的Memcache实例，和Store是互相引用
     * @param memcache Memcache实例
     * @author yinlei
     * date 2013-4-5 下午1:53:26
     */
    public void setMemcache(Memcache memcache);

}
