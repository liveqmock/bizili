package com.vteba.cache.memcached.transaction.local;

import com.vteba.cache.memcached.transaction.TransactionID;
import com.vteba.cache.memcached.transaction.TransactionIDFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The local transactions mode recovery manager which is used to trigger full recovery of all
 * caches configured with local transaction mode.
 * 本地事务模式恢复管理器，被用来触发恢复配置为本地事务模式的缓存
 * @author Ludovic Orban
 */
public class LocalRecoveryManager {

	/**
	 * 事务ID工厂
	 */
    private final TransactionIDFactory transactionIdFactory;
    
    /**
     * 本地事务存储List，CopyOnWriteArrayList实现
     */
    private final List<LocalTransactionStore> localTransactionStores = new CopyOnWriteArrayList<LocalTransactionStore>();
    
    /**
     * 以前恢复的事务IDs
     */
    private volatile Set<TransactionID> previouslyRecoveredTransactionIDs = Collections.emptySet();

    /**
     * Create a LocalRecoveryManager instance
     * @param transactionIdFactory the TransactionIDFactory
     */
    public LocalRecoveryManager(TransactionIDFactory transactionIdFactory) {
        this.transactionIdFactory = transactionIdFactory;
    }

    /**
     * Register a LocalTransactionStore from the recovery manager
     * 向恢复管理器注册一个LocalTransactionStore
     * @param localTransactionStore the LocalTransactionStore
     */
    void register(LocalTransactionStore localTransactionStore) {
        localTransactionStores.add(localTransactionStore);
    }

    /**
     * Unregister a LocalTransactionStore from the recovery manager
     * 从恢复管理器卸载一个LocalTransactionStore
     * @param localTransactionStore the LocalTransactionStore
     */
    void unregister(LocalTransactionStore localTransactionStore) {
        localTransactionStores.remove(localTransactionStore);
    }

    /**
     * Run recovery on all registered local transaction stores. The latter
     * are used internally by caches when they're configured with local transaction mode.
     * 运行恢复所有已注册的本地事务存储。后者内部使用配置了本地事务模式高速缓存时。
     * @return the set of recovered TransactionIDs。返回被回复的事务ID集合
     */
    public Set<TransactionID> recover() {
        Set<TransactionID> recovered = new HashSet<TransactionID>();
        // first ask all stores to cleanup their soft locks
        for (LocalTransactionStore localTransactionStore : localTransactionStores) {
            recovered.addAll(localTransactionStore.recover());
        }
        // then clear the transaction ID
        for (TransactionID transactionId : recovered) {
            transactionIdFactory.clear(transactionId);
        }

        previouslyRecoveredTransactionIDs = recovered;
        return recovered;
    }

    /**
     * Get the set of transaction IDs collected by the previous recover() call
     * 返回recover()方法收集的事务IDs
     * @return the set of previously recovered TransactionIDs
     */
    public Set<TransactionID> getPreviouslyRecoveredTransactionIDs() {
        return previouslyRecoveredTransactionIDs;
    }
}
