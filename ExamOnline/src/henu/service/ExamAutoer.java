package henu.service;

import redis.clients.jedis.Tuple;

/**
 * @Describtion: (用一个SortSet管理考试信息，这里提供添加和删除方法；修改同添加方法一致). <br/> 
 * @date: 2018年6月5日 下午4:46:23 <br/> 
 * @author Beats <br/> 
 * @version v1.0 <br/>
 * @since JDK 1.8
 */
public interface ExamAutoer {

	long queueBegin(int examId, long timeLeft);

	long queueClose(int examId, long timeLeft);

	long dequeueBegin(int examId);

	long dequeueClose(int examId);

	Tuple getRecentBeginExam();

	Tuple getRecentCloseExam();
	
}
