package com.vteba.cache.memcached.transaction.local;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.vteba.cache.hibernate.CacheException;
import com.vteba.cache.hibernate.memcached.Memcache;
import com.vteba.cache.memcached.Element;
import com.vteba.cache.memcached.MemcacheClientDelegate;
import com.vteba.cache.memcached.Store;
import com.vteba.cache.memcached.store.ElementValueComparator;
import com.vteba.cache.memcached.transaction.TransactionController;
import com.vteba.cache.memcached.transaction.TransactionID;

/**
 * 本地事务存储的委托类，包装本地事务
 * @author yinlei
 * date 2013-4-4 下午11:35:02
 */
public class LocalTransactionStoreDelegete implements Store{
	private LocalTransactionStore localTransactionStore;
	private final TransactionController transactionController;
	
	/**
	 * 构建LocalTransactionStoreDelegete的委托实例
	 * @param localTransactionStore LocalTransactionStore被委托对象
	 */
	public LocalTransactionStoreDelegete(LocalTransactionStore localTransactionStore) {
		super();
		this.localTransactionStore = localTransactionStore;
		this.transactionController = localTransactionStore.getTransactionController();
	}

	public void expireElements() {
		localTransactionStore.expireElements();
	}

	public int hashCode() {
		return localTransactionStore.hashCode();
	}

	public boolean equals(Object obj) {
		return localTransactionStore.equals(obj);
	}

	public Set<TransactionID> recover() {
		return localTransactionStore.recover();
	}

	public boolean put(Element element) {
		try {
			transactionController.begin();
			return localTransactionStore.put(element);
		} catch (Exception e) {
			transactionController.rollback();
			return false;
		} finally {
			transactionController.commit();
		}
	}

	public String toString() {
		return localTransactionStore.toString();
	}

	public Element get(String key) {
		try {
			transactionController.begin();
			return localTransactionStore.get(key);
		} catch (Exception e) {
			transactionController.rollback();
			return null;
		} finally {
			transactionController.commit();
		}
	}

	public Element remove(String key) {
		try {
			transactionController.begin();
			return localTransactionStore.remove(key);
		} catch (Exception e) {
			transactionController.rollback();
			return null;
		} finally {
			transactionController.commit();
		}
	}

	public Element putIfAbsent(Element e) throws NullPointerException {
		try {
			transactionController.begin();
			return localTransactionStore.putIfAbsent(e);
		} catch (Exception e1) {
			transactionController.rollback();
			return null;
		} finally {
			transactionController.commit();
		}
	}

	public Element removeElement(Element e, ElementValueComparator comparator)
			throws NullPointerException {
		try {
			transactionController.begin();
			return localTransactionStore.removeElement(e, comparator);
		} catch (Exception e1) {
			transactionController.rollback();
			return null;
		} finally {
			transactionController.commit();
		}
	}

	public boolean replace(Element oe, Element ne,
			ElementValueComparator comparator) throws NullPointerException,
			IllegalArgumentException {
		try {
			transactionController.begin();
			return localTransactionStore.replace(oe, ne, comparator);
		} catch (Exception e1) {
			transactionController.rollback();
			return false;
		} finally {
			transactionController.commit();
		}
	}

	public void putAll(Collection<Element> elements) throws CacheException {
		try {
			transactionController.begin();
			localTransactionStore.putAll(elements);
		} catch (Exception e1) {
			transactionController.rollback();
		} finally {
			transactionController.commit();
		}
	}

	public void removeAll(Collection<String> keys) {
		try {
			transactionController.begin();
			localTransactionStore.removeAll(keys);
		} catch (Exception e1) {
			transactionController.rollback();
		} finally {
			transactionController.commit();
		}
	}

	public Map<String, Element> getAll(Collection<String> keys) {
		try {
			transactionController.begin();
			return localTransactionStore.getAll(keys);
		} catch (Exception e1) {
			transactionController.rollback();
			return Collections.emptyMap();
		} finally {
			transactionController.commit();
		}
	}

	public List<String> getKeys() {
		return localTransactionStore.getKeys();
	}

	public boolean containsKey(String key) {
		try {
			transactionController.begin();
			return localTransactionStore.containsKey(key);
		} catch (Exception e1) {
			transactionController.rollback();
			return false;
		} finally {
			transactionController.commit();
		}
	}

//	public void removeAll() throws CacheException {
//		try {
//			transactionController.begin();
//			localTransactionStore.removeAll();
//		} catch (Exception e1) {
//			transactionController.rollback();
//		} finally {
//			transactionController.commit();
//		}
//	}

	public MemcacheClientDelegate getMemcacheClientDelegate() {
		return localTransactionStore.getMemcacheClientDelegate();
	}

	@Override
	public Memcache getMemcache() {
		return localTransactionStore.getMemcache();
	}

	@Override
	public void setMemcache(Memcache memcache) {
		localTransactionStore.setMemcache(memcache);
	}
	
}
