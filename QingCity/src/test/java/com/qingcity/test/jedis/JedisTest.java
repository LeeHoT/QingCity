package com.qingcity.test.jedis;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;

public class JedisTest {
	public static void main(String[] args) {
		// 连接本地的Redis服务
		Jedis jedis = new Jedis("localhost");
		System.out.println("Connection to server successfully");
		//查看服务是否运行
		System.out.println(jedis.ping());
		//设置redis字符串数据
		jedis.set("leekey", "Redis tutorial");
		//获取存储的数据并输出
		System.out.println("   Stored string in redis: "+jedis.get("leekey"));
		
//		jedis.lpush("tutorial-list", "Redis");
//		jedis.lpush("tutorial-list", "Mongodb");
//		jedis.lpush("tutorial-list", "Mysql");
//		
//		List<String> list = jedis.lrange("tutorial-list", 0, 5);
//		for(int i = 0 ; i<list.size();i++){
//			System.out.println("Stored string in redis:: "+list.get(i));
//		}
		
		Set<String> list = jedis.keys("*");
		Iterator<String> it = list.iterator();
		while(it.hasNext()){
			System.out.println(it.next());
		}
	}

}
