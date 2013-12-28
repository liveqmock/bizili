package com.vteba.cache.memcached.transaction.local;

/**
 * A listener interface to get called back when a transaction is being terminated
 * 事务被中断时的回调监听器接口
 * @author Ludovic Orban
 */
public interface TransactionListener {

    /**
     * This method gets called right before the transaction is committed
     * 该方法将在事务提交前回调
     */
    void beforeCommit();

    /**
     * This method gets called right after the transaction is committed
     * 该方法将在事务提交后回调
     */
    void afterCommit();

    /**
     * This method gets called right after the transaction is rolled back
     * 该方法将在事务回滚后回调
     */
    void afterRollback();

}
