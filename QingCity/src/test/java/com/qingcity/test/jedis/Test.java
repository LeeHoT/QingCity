package com.qingcity.test.jedis;

import redis.clients.jedis.Jedis;

public class Test {
	public static void main(String[] args) {
		Jedis js = new Jedis("127.0.0.1", 6379);
		String val = js.get("key001");
		System.out.println(val);
	}
}
