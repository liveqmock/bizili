package com.vteba.cache.hibernate.memcached.access;

import java.util.concurrent.Callable;
import javax.transaction.Status;
import javax.transaction.TransactionManager;

/**
 * Helper for dealing with Infinisan cache instances.
 */
public class CacheHelper {

   /**
    * Disallow external instantiation of CacheHelper.
    */
   private CacheHelper() {
   }

   public static <T> T withinTx(TransactionManager tm, Callable<T> c) throws Exception {
      tm.begin();
      try {
         return c.call();
      } catch (Exception e) {
         tm.setRollbackOnly();
         throw e;
      } finally {
         if (tm.getStatus() == Status.STATUS_ACTIVE) tm.commit();
         else tm.rollback();
      }
   }

}
