package com.vteba.cache.hibernate.memcached.xmemcached;

import net.rubyeye.xmemcached.CommandFactory;
import net.rubyeye.xmemcached.HashAlgorithm;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.MemcachedSessionLocator;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.command.TextCommandFactory;
import net.rubyeye.xmemcached.impl.ArrayMemcachedSessionLocator;
import net.rubyeye.xmemcached.impl.KetamaMemcachedSessionLocator;
import net.rubyeye.xmemcached.impl.PHPMemcacheSessionLocator;
import net.rubyeye.xmemcached.utils.AddrUtil;

import com.vteba.cache.hibernate.PropertiesHelper;
import com.vteba.cache.hibernate.memcached.Config;
import com.vteba.cache.hibernate.memcached.MemcacheClientFactory;
import com.vteba.cache.memcached.MemcacheClientDelegate;
import com.vteba.cache.memcached.MemcacheStore;
import com.vteba.cache.memcached.Store;

/**
 * Parses hibernate properties to produce a MemcachedClient。
 * 解析hibernate配置属性，产生MemcachedClient。
 * See {@link com.vteba.cache.hibernate.memcached.MemcacheClientFactory}
 * @author yinlei
 */
public class XMemcacheClientFactoryImpl implements MemcacheClientFactory {

	public static final String PROP_SERVERS = Config.PROP_PREFIX + "servers";//server地址
	public static final String PROP_READ_BUFFER_SIZE = Config.PROP_PREFIX + "readBufferSize";//读缓冲区大小
	public static final String PROP_OPERATION_TIMEOUT = Config.PROP_PREFIX + "operationTimeout";//操作超时时间
	public static final String PROP_HASH_ALGORITHM = Config.PROP_PREFIX + "hashAlgorithm";//hash算法
	public static final String PROP_COMMAND_FACTORY = Config.PROP_PREFIX + "commandFactory";//传输协议
	public static final String PROP_SESSION_LOCATOR = Config.PROP_PREFIX + "sessionLocator";//session定位器
	public static final String PROP_CONNECTION_POOL_SIZE = Config.PROP_PREFIX + "connectionPoolSize";//连接池大小
	public static final String PROP_CONNECT_TIMEOUT = Config.PROP_PREFIX + "connectTimeout";//连接超时时间
	private final PropertiesHelper properties;

	public XMemcacheClientFactoryImpl(PropertiesHelper properties) {
		this.properties = properties;
	}

//	public Memcache createMemcacheClient() throws Exception {
//		MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(getServerList()));
//		builder.setCommandFactory(getCommandFactory());
//		builder.setSessionLocator(getSessionLocator());
//		builder.getConfiguration().setSessionReadBufferSize(getReadBufferSize());
//		builder.setConnectionPoolSize(getConnectionPoolSize());
//		builder.setConnectTimeout(getConnectTimeoutMillis());
//		MemcachedClient client = builder.build();
//		client.setOpTimeout(getOperationTimeoutMillis());
//		return null;//new Xmemcache(client);
//	}

	protected MemcachedSessionLocator getSessionLocator() {
		if (sessionLocatorNameEquals(ArrayMemcachedSessionLocator.class)) {
			return new ArrayMemcachedSessionLocator(getHashAlgorithm());
		}

		if (sessionLocatorNameEquals(KetamaMemcachedSessionLocator.class)) {
			return new KetamaMemcachedSessionLocator(getHashAlgorithm());
		}

		if (sessionLocatorNameEquals(PHPMemcacheSessionLocator.class)) {
			return new PHPMemcacheSessionLocator(getHashAlgorithm());
		}

		throw new IllegalArgumentException("Unsupported "
				+ PROP_SESSION_LOCATOR + " value: " + getCommandFactoryName());
	}

	protected CommandFactory getCommandFactory() {
		if (commandFactoryNameEquals(TextCommandFactory.class)) {
			return new TextCommandFactory();
		}

		if (commandFactoryNameEquals(BinaryCommandFactory.class)) {
			return new BinaryCommandFactory();
		}

		throw new IllegalArgumentException("Unsupported "
				+ PROP_COMMAND_FACTORY + " value: " + getCommandFactoryName());
	}

	private boolean commandFactoryNameEquals(Class<?> cls) {
		return cls.getSimpleName().equals(getCommandFactoryName());
	}
	
	private boolean sessionLocatorNameEquals(Class<?> cls) {
		return cls.getSimpleName().equals(getSessionLocatorName());
	}

	/**
	 * memcache server 地址列表。如10.11.21.1:11211,10.11.21.2:11212
	 * @author yinlei
	 * date 2012-11-22 下午10:07:20
	 */
	public String getServerList() {
		return this.properties.get(PROP_SERVERS, "localhost:11211");
	}

	public int getReadBufferSize() {
		return this.properties.getInt(PROP_READ_BUFFER_SIZE,
				MemcachedClient.DEFAULT_SESSION_READ_BUFF_SIZE);
	}
	
	/**
	 * 连接池大小，默认1
	 * @author yinlei
	 * date 2012-11-22 下午10:06:36
	 */
    public int getConnectionPoolSize() {
        return this.properties.getInt(PROP_CONNECTION_POOL_SIZE,
                MemcachedClient.DEFAULT_CONNECTION_POOL_SIZE);
    }

	public long getOperationTimeoutMillis() {
		return this.properties.getLong(PROP_OPERATION_TIMEOUT,
				MemcachedClient.DEFAULT_OP_TIMEOUT);
	}

	public long getConnectTimeoutMillis() {
        return this.properties.getLong(PROP_CONNECT_TIMEOUT,
                MemcachedClient.DEFAULT_CONNECT_TIMEOUT);
    }
	
	public HashAlgorithm getHashAlgorithm() {
		return this.properties.getEnum(PROP_HASH_ALGORITHM,
				HashAlgorithm.class, HashAlgorithm.NATIVE_HASH);
	}

	public String getCommandFactoryName() {
		return this.properties.get(PROP_COMMAND_FACTORY,
				TextCommandFactory.class.getSimpleName());
	}

	public String getSessionLocatorName() {
		return this.properties.get(PROP_SESSION_LOCATOR,
				ArrayMemcachedSessionLocator.class.getSimpleName());
	}

	protected PropertiesHelper getProperties() {
		return this.properties;
	}

	@Override
	public Store createMemcacheStore() throws Exception {
		MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(getServerList()));
		builder.setCommandFactory(getCommandFactory());
		builder.setSessionLocator(getSessionLocator());
		builder.getConfiguration().setSessionReadBufferSize(getReadBufferSize());
		builder.setConnectionPoolSize(getConnectionPoolSize());
		builder.setConnectTimeout(getConnectTimeoutMillis());
		MemcachedClient client = builder.build();
		client.setOpTimeout(getOperationTimeoutMillis());
		MemcacheClientDelegate delegate = new MemcacheClientDelegate(client);
		return new MemcacheStore(delegate);
	}
}
