package com.explorer2.setting;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class UrlRBF {

	private static JedisPool pool = null;

	private static final int[] seeds = new int[] { 3, 7, 11, 13, 31, 37, 55 };

	private static final String redisHost = "127.0.0.1";
	private static final int redisPort = 6379;
	private static final String redisPass = "gdw123";
	public String key = null;

	private BloomHash[] func = new BloomHash[seeds.length];

	public UrlRBF(String key) {
		this.key = key;
		for (int i = 0; i < seeds.length; i++) {
			func[i] = new BloomHash(2 << 26, seeds[i]);
		}
	}

	/**
	 * 加入一个数据
	 * 
	 * @param value
	 */
	public void add(String value) {
		for (BloomHash f : func) {
			setBig(f.hash(value), true);
		}
	}

	/**
	 * 判重
	 * 
	 * @param value
	 * @return
	 */
	public boolean contains(String value) {
		if (value == null) {
			return false;
		}
		boolean ret = true;
		for (BloomHash f : func) {
			ret = ret && getBig(f.hash(value));
		}
		return ret;
	}

	/**
	 * redis连接池初始化并返回一个redis连接
	 * 
	 * @return redis连接实例
	 */
	private Jedis getJedis() {
		if (pool == null) {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(300);
			config.setMaxIdle(100);
			config.setMaxWaitMillis(20000);
			config.setTestOnBorrow(true);
			pool = new JedisPool(config, redisHost, redisPort, 20000, redisPass);
		}
		return pool.getResource();
	}

	private boolean setBig(int offset, boolean value) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.setbit(key, offset, value);
		} catch (Exception e) {
			return true;
		} finally {
			returnResource(jedis);
		}
	}

	private boolean getBig(int offset) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.getbit(key, offset);
		} catch (Exception e) {
			// redis异常，返回true,保证bloomfilter规则之存在的元素一定是返回存在
			e.printStackTrace();
			return true;
		} finally {
			returnResource(jedis);
		}
	}

	private void returnResource(Jedis redis) {
		if (redis != null) {
			pool.returnResource(redis);
		}
	}

	/**
	 * 一个简单的hash算法类，输出int类型hash值
	 * 
	 * @author zhiyong.xiongzy
	 *
	 */
	public static class BloomHash {

		private int cap;
		private int seed;

		public BloomHash(int cap, int seed) {
			this.cap = cap;
			this.seed = seed;
		}

		public int hash(String value) {
			int result = 0;
			int len = value.length();
			for (int i = 0; i < len; i++) {
				result = seed * result + value.charAt(i);
			}
			return (cap - 1) & result;
		}
	}
/*	public static void main(String[] args){
		UrlRBF a=new UrlRBF("jobinfo");
		
		if(a.contains("网站稳定性工程师,专家-18-42万-上海-浦东新区-本科及以上 3年以上经验 英语 + 普通话 25-40岁-阿里巴巴-2017-05-15-")){
			System.out.println("yes");
		}else{
			System.out.println("no");
		}
	}*/

}