package henu.dao;

import java.util.Set;

import redis.clients.jedis.Tuple;

public interface JedisClient {

	String get(String key);

	String set(String key, String value);

	long ttl(String key);

	long expire(String key, int second);

	long incr(String key);

	long hset(String hkey, String key, String value);

	String hget(String hkey, String key);
	
	long del(String key);
	
	long hdel(String hkey, String key);

	long zadd(String key, double score, String member);

	long zrem(String key, String... members);

	Set<Tuple> zrange(String key, long start, long end);
}
