package henu.service.impl;

import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import henu.dao.JedisClient;
import henu.service.ExamAutoer;
import redis.clients.jedis.Tuple;

@Service
public class ExamAutoerImpl implements ExamAutoer {

	@Resource
	private JedisClient jedisClient;

	@Value("${exam_auto_start}")
	private String exam_auto_start;
	@Value("${exam_auto_close}")
	private String exam_auto_close;
	
	@Override
	public long queueBegin(int examId, long timeLeft) {
		long res = jedisClient.zadd(exam_auto_start, timeLeft, examId+"");
		return res;
	}
	
	@Override
	public long queueClose(int examId, long timeLeft) {
		long res = jedisClient.zadd(exam_auto_close, timeLeft, examId+"");
		return res;
	}

	@Override
	public long dequeueBegin(int examId) {
		long res = jedisClient.zrem(exam_auto_start, examId+"");
		return res;
	}
	
	@Override
	public long dequeueClose(int examId) {
		long res = jedisClient.zrem(exam_auto_close, examId+"");
		return res;
	}
	
	@Override
	public Tuple getRecentBeginExam() {
		Set<Tuple> set = jedisClient.zrange(exam_auto_start, 0, 0);
		if (set != null && set.size() > 0) {
			Tuple res = set.iterator().next();
			return res;
		}
		return null;
	}
	
	@Override
	public Tuple getRecentCloseExam() {
		Set<Tuple> set = jedisClient.zrange(exam_auto_close, 0, 0);
		if (set != null && set.size() > 0) {
			Tuple res = set.iterator().next();
			return res;
		}
		return null;
	}
}
