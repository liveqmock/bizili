package com.vteba.cache.redis;

import redis.clients.jedis.Jedis;

public class RedisClientService {
	private static Jedis jedis = new Jedis("127.0.0.1", 13000);
	
	public Long append() {
		return jedis.append("key", "value");
	}
}
