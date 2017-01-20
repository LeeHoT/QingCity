package com.qingcity.utils;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public final class RedisUtil {

	// Redis服务器IP
	private static String ADDR = "192.168.168.105";

	// Redis的端口号
	private static int PORT = 6379;

	// 访问密码
	private static String AUTH = "root";

	// 可用连接实例的最大数目，默认值为8;
	// 如果赋值为-1 ，则表示不限制;如果pool已经分配了maxActive个jedis实例，则此时pool的状态exhausted(耗尽)
	private static int MAX_ACTIVE = 1024;

	// 控制一个pool最多有对少个状态为idle(空闲的)的jedis实例,默认值也是8
	private static int MAX_IDLE = 200;

	// 等待可用连接的最大时间、单位毫秒，默认值为-1，表示永远不超时，如果超过等待时间，则直接抛出JedisConnectionException
	private static int MAX_WAIT = 10000;

	private static int TIMEOUT = 10000;

	// 在borrow一个jedis实例，是否提前进行validate操作;如果为true 则得到的jedis实例均是可用的;
	private static boolean TEST_ON_BORROW = true;

	private static JedisPool jedisPool = null;

	/**
	 * 初始化Redis连接池
	 */
	static {
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxIdle(MAX_IDLE);
			// config.setMaxActive(MAX_ACTIVE);
			//config.setMaxWait(MAX_WAIT);
			

		} catch (Exception e) {
			ExceptionUtils.getStackTrace(e);

		}
	}
}
