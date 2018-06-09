package henu.dao.impl;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import henu.dao.JedisClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

@Repository
public class JedisClientSingle implements JedisClient {

	@Resource
	private JedisPool jedisPool;
	
	@Override
	public String get(String key) {
		Jedis jedis = jedisPool.getResource();
		String string = jedis.get(key);
		jedis.close();
		return string;
	}

	@Override
	public String set(String key, String value) {
		Jedis jedis = jedisPool.getResource();
		String string = jedis.set(key, value);
		jedis.close();
		return string;
	}

	@Override
	public String hget(String hkey, String key) {
		Jedis jedis = jedisPool.getResource();
		String string = jedis.hget(hkey, key);
		jedis.close();
		return string;
	}

	@Override
	public long hset(String hkey, String key, String value) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.hset(hkey, key, value);
		jedis.close();
		return result;
	}

	@Override
	public long incr(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.incr(key);
		jedis.close();
		return result;
	}

	@Override
	public long expire(String key, int second) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.expire(key, second);
		jedis.close();
		return result;
	}

	@Override
	public long ttl(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.ttl(key);
		jedis.close();
		return result;
	}

	@Override
	public long del(String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.del(key);
		jedis.close();
		return result;
	}

	@Override
	public long hdel(String hkey, String key) {
		Jedis jedis = jedisPool.getResource();
		Long result = jedis.hdel(hkey, key);
		jedis.close();
		return result;
	}
	
	@Override
	public long zadd(String key, double score, String member) {
		Jedis jedis = jedisPool.getResource();
		long res = jedis.zadd(key, score, member);
		jedis.close();
		return res;
	}
	
	@Override
	public Set<Tuple> zrange(String key, long start, long end) {
		Jedis jedis = jedisPool.getResource();
		Set<Tuple> set = jedis.zrangeWithScores(key, start, end);
		jedis.close();
		return set;
	}
	
	@Override
	public long zrem(String key, String... members) {
		Jedis jedis = jedisPool.getResource();
		long zrem = jedis.zrem(key, members);
		jedis.close();
		return zrem;
	}
}
