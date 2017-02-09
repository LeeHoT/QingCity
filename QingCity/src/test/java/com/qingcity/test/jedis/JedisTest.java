package com.qingcity.test.jedis;

import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;

public class JedisTest {

	public static void main(String[] args) {
		 Jedis js = new Jedis("127.0.0.1", 6379);
		//
		// js.set("key001", "redis001");
		// String val = js.get("key001");
		// System.out.println(val);
		// js.del("key001");

		/************************** 测试Redis的数据类型 **************************/

		/**
		 * list
		 */
		js.rpush("list1", "aaaaaaaaaaaaaaaaaaaaaa");
		js.rpush("list1", "bbbbbbbbbbbbbbbbbbbbbb");
		js.rpush("list1", "ccccccccccccccccccccc");
		js.rpush("list1", "dddddddddddddd");
		List<String> vals = js.lrange("list1", 0, -1);
		for (int i = 0; i < vals.size(); i++) {
			System.out.println(vals.get(i));
		}

		/**
		 * set 无须唯一
		 */
		 js.sadd("s1", "顺序3");
		 js.sadd("s1", "a");
		 js.sadd("s1", "b");
		 js.sadd("s1", "1");
		 js.sadd("s1", "蛤蛤蛤");
		 js.sadd("s1", "2");
		 js.sadd("s1", "so waht？");
		 js.sadd("s1", "%^");
		 js.sadd("s1", "顺序1");
		 js.sadd("s1", "乱码吗？");
		 js.sadd("s1", "顺序2");
		 Set<String> s = js.smembers("s1");
		 for (String string : s) {
		 System.out.println(s);
		 }
		 js.srem("s1", "蛤蛤蛤");

		/**
		 * zset(sorted set 有序集合)
		 * 有2中编码类型:ziplist,skiplist,当zset中数据较多时,将会被重构为skiplist
		 */
		// js.zadd("zs", 92, "张三1");
		// js.zadd("zs", 93, "张三7");
		// js.zadd("zs", 94, "张三5");
		// js.zadd("zs", 87, "张三9");
		// js.zadd("zs", 66, "张三");
		// js.zadd("zs", 19, "张三0");
		// Set<String> sets = js.zrange("zs", 0, -1);
		// for (String string : sets) {
		// System.out.println(sets);
		// }

		/**
		 * Hash
		 */
		// Map m = new HashMap();
		// m.put("1", "t");
		// m.put("2", "ttt");
		// m.put("username", "老王");
		// m.put("password", "123456");
		// m.put("age", "79");
		// m.put("sex", "man");
		// js.hmset("m", m);
		// List<String> v = js.hmget("m", new String[]{"username","age"});
		// List<String> v1 = js.hmget("m", "sex");
		// System.out.println(v);
		// System.out.println(v1);
		// js.hdel("m", "username");//删除map中的某一个键的键值对

		/************************** 事务控制 **************************/

		/**
		 * 事务方式(Transactions)
		 * 他主要目的是保障，一个client发起的事务中的命令可以连续的执行，而中间不会插入其他client的命令。
		 * 
		 * 我们调用jedis.watch(…)方法来监控key，如果调用后key值发生变化，则整个事务会执行失败。
		 * 另外，事务中某个操作失败，并不会回滚其他操作。这一点需要注意。 还有，我们可以使用discard()方法来取消事务。
		 */
		// Jedis js1 = new Jedis("127.0.0.1", 6379);
		// long s = System.currentTimeMillis();
		// Transaction tx = js1.multi();
		// for (int i = 0; i < 99999; i++) {
		// tx.set("keyttt"+i, "valttt"+i);
		// }
		// List<Object> res= tx.exec();
		// long e = System.currentTimeMillis();
		// System.out.println((e-s)/1000.0+"秒");
		// System.out.println(res);
		// js1.disconnect();

		/************************** 管道 **************************/
		/**
		 * 管道(Pipelining) 有时，我们需要采用异步方式，一次发送多个指令，不同步等待其返回结果。
		 * 这样可以取得非常好的执行效率。这就是管道
		 */
		// Jedis js2 = new Jedis("127.0.0.1", 6379);
		// long s = System.currentTimeMillis();
		// Pipeline pe = js2.pipelined();
		// for (int i = 0; i < 9999; i++) {
		// pe.set("keya"+i, "valuea"+i);
		// }
		// List<Object> l = pe.syncAndReturnAll();
		// long e = System.currentTimeMillis();
		// System.out.println((e-s)/1000.0+"秒");
		// js2.disconnect();

		/************************** 管道中调用事务 **************************/
		/**
		 * 管道中调用事务 在用法上看，管道中包含了事务
		 */

		// Jedis js3 = new Jedis("127.0.0.1", 6379);
		// long s = System.currentTimeMillis();
		// Pipeline pe = js3.pipelined();
		// pe.multi();
		// for (int i = 0; i < 9999; i++) {
		// pe.set("keybb"+i, "valuebb"+i);
		// }
		// pe.exec();
		// List<Object> l = pe.syncAndReturnAll();
		// long e = System.currentTimeMillis();
		// System.out.println((e-s)/1000.0+"秒");
		// js3.disconnect();

		/************************** 分布式直连同步调用 **************************/
		/**
		 * 分布式直连同步调用 线程不安全的，不建议在线程池中使用直连
		 */
		// List<JedisShardInfo> shards = Arrays.asList(
		// new JedisShardInfo("localhost",6379),
		// new JedisShardInfo("localhost",6380));
		// ShardedJedis sharding = new ShardedJedis(shards);
		// long start = System.currentTimeMillis();
		// for (int i = 0; i < 100000; i++) {
		// String result = sharding.set("sn" + i, "n" + i);
		// }
		// long end = System.currentTimeMillis();
		// System.out.println("Simple@Sharing SET: " + ((end - start)/1000.0) +
		// " seconds");
		// sharding.disconnect();

		/************************** 分布式直连同步调用 **************************/
		/**
		 * 分布式直连异步调用 线程不安全的，不建议在线程池中使用直连
		 */
		// List<JedisShardInfo> shards = Arrays.asList(
		// new JedisShardInfo("localhost",6379),
		// new JedisShardInfo("localhost",6380));
		// ShardedJedis sharding = new ShardedJedis(shards);
		// ShardedJedisPipeline pipeline = sharding.pipelined();
		// long start = System.currentTimeMillis();
		// for (int i = 0; i < 100000; i++) {
		// pipeline.set("sp" + i, "p" + i);
		// }
		// List<Object> results = pipeline.syncAndReturnAll();
		// long end = System.currentTimeMillis();
		// System.out.println("Pipelined@Sharing SET: " + ((end - start)/1000.0)
		// + " seconds");
		// sharding.disconnect();

		/************************** 分布式连接池同步调用 **************************/
		/**
		 * 同步方式
		 */
		// List<JedisShardInfo> shards = Arrays.asList(
		// new JedisShardInfo("localhost",6379),
		// new JedisShardInfo("localhost",6380));
		//
		// ShardedJedisPool pool = new ShardedJedisPool(new JedisPoolConfig(),
		// shards);
		//
		// ShardedJedis one = pool.getResource();
		//
		// long start = System.currentTimeMillis();
		// for (int i = 0; i < 100000; i++) {
		// String result = one.set("spn" + i, "n" + i);
		// }
		// long end = System.currentTimeMillis();
		// pool.returnResource(one);
		// System.out.println("Simple@Pool SET: " + ((end - start)/1000.0) + "
		// seconds");
		//
		// pool.destroy();
		//

		/************************** 分布式连接池异步调用 **************************/
		/**
		 * 异步方式
		 */
		// List<JedisShardInfo> shards = Arrays.asList(
		// new JedisShardInfo("localhost",6379),
		// new JedisShardInfo("localhost",6380));
		//
		// ShardedJedisPool pool = new ShardedJedisPool(new JedisPoolConfig(),
		// shards);
		//
		// ShardedJedis one = pool.getResource();
		//
		// ShardedJedisPipeline pipeline = one.pipelined();
		//
		// long start = System.currentTimeMillis();
		// for (int i = 0; i < 100000; i++) {
		// pipeline.set("sppn" + i, "n" + i);
		// }
		// List<Object> results = pipeline.syncAndReturnAll();
		// long end = System.currentTimeMillis();
		// pool.returnResource(one);
		// System.out.println("Pipelined@Pool SET: " + ((end - start)/1000.0) +
		// " seconds");
		// pool.destroy();

		/************************** 其他 **************************/

		/**
		 * 清空所有
		 */
		// js.flushAll();

		/**
		 * 销毁链接
		 */
		// js.disconnect();

	}
}