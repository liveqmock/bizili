package com.vteba.cache.infinispan;

import org.infinispan.api.BasicCacheContainer;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.Configuration;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.transaction.TransactionMode;
import org.infinispan.transaction.lookup.GenericTransactionManagerLookup;

public class InfinispanConfiguration {
	@SuppressWarnings("unused")
	public void test() {
		GlobalConfiguration glob = new GlobalConfigurationBuilder()
	    .nonClusteredDefault().build(); 

	Configuration loc = new ConfigurationBuilder()
	    .clustering().cacheMode(CacheMode.LOCAL)
	      .transaction().transactionMode(TransactionMode.TRANSACTIONAL)
	    .autoCommit(false)
	    .transactionManagerLookup(new GenericTransactionManagerLookup())
	    .loaders().passivation(false).preload(false).shared(false)
	    //.addCacheLoader().cacheLoader(new JdbcStringBasedCacheStore())
	    //.fetchPersistentState(false).purgeOnStartup(true)
//	    .addProperty("stringsTableNamePrefix", "carmart_table")
//	    .addProperty("idColumnName", "ID_COLUMN")
//	    .addProperty("dataColumnName", "DATA_COLUMN")
//	    .addProperty("timestampColumnName", "TIMESTAMP_COLUMN")
//	    .addProperty("timestampColumnType", "BIGINT")
//	    .addProperty("connectionFactoryClass",
//	        "org.infinispan.loaders.jdbc.connectionfactory.PooledConnectionFactory")
//	    .addProperty("connectionUrl", "jdbc:mysql://localhost:3306/carmartdb")
//	    .addProperty("userName", "carmart")   //we do not have a managed datasource 
//	     //-> specify credentials here
//	    .addProperty("password", "carmart")
//	    .addProperty("driverClass", "com.mysql.jdbc.Driver")
//	    .addProperty("idColumnType", "VARCHAR(255)")
//	    .addProperty("dataColumnType", "VARBINARY(1000)")
//	    .addProperty("dropTableOnExit", "false")
//	    .addProperty("createTableOnStart", "true")
//	    .addProperty("databaseType", "MYSQL")
	    //.addProperty("datasourceJndiLocation", "java:jboss/datasources/ExampleDS") 
	    //oh, yes, we do not use JNDI now     
	    .build();
	}
	
	@SuppressWarnings({ "deprecation", "unused" })
	public void test2() {
		GlobalConfiguration glob = new GlobalConfigurationBuilder()
		   .nonClusteredDefault().build();
		 
		Configuration loc = new ConfigurationBuilder()
		   .clustering().cacheMode(CacheMode.LOCAL) 
		   .transaction().transactionMode(TransactionMode.TRANSACTIONAL)
		   .autoCommit(false)
		   .transactionManagerLookup(new GenericTransactionManagerLookup())
		   .loaders().passivation(false).preload(false).shared(false)
		   .addCacheLoader().cacheLoader(null)
		   .fetchPersistentState(false).purgeOnStartup(true)
		   .addProperty("stringsTableNamePrefix", "carmart_table")
		   .addProperty("idColumnName", "ID_COLUMN")
		   .addProperty("dataColumnName", "DATA_COLUMN")
		   .addProperty("timestampColumnName", "TIMESTAMP_COLUMN")
		    //for different DB, use different type
		   .addProperty("timestampColumnType", "BIGINT") 
		   .addProperty("connectionFactoryClass", 
		        "org.infinispan.loaders.jdbc.connectionfactory.ManagedConnectionFactory")
		   .addProperty("connectionUrl", "jdbc:mysql://localhost:3306/carmartdb")
		   .addProperty("driverClass", "com.mysql.jdbc.Driver")
		    //for different DB, use different type 
		   .addProperty("idColumnType", "VARCHAR(255)") 
		    //for different DB, use different type 
		   .addProperty("dataColumnType", "VARBINARY(1000)") 
		   .addProperty("dropTableOnExit", "false")
		   .addProperty("createTableOnStart", "true")
		   .addProperty("databaseType", "MYSQL")
		   .addProperty("datasourceJndiLocation", "java:jboss/datasources/ExampleDS")
		   .build();

		BasicCacheContainer manager = new DefaultCacheManager(glob, loc, true); 
		//.... = manager.getCache() 
	}
}
