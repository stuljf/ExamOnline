package henu.quartz;

import java.sql.SQLException;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import henu.dao.ExamDao;
import henu.dao.impl.ExamDaoImpl;
import henu.service.ExamAutoer;
import henu.service.impl.ExamAutoerImpl;
import henu.util.ExceptionUtil;
import redis.clients.jedis.Tuple;

/**
 * @Describtion: (考试状态扫描，用于自动开启或关闭考试). <br/> 
 * @date: 2018年6月5日 下午6:02:37 <br/> 
 * @author Beats <br/> 
 * @version v1.0 <br/>
 * @since JDK 1.8
 */
//@Component
public class ExamStatusScanJob implements Job {

	private Logger log = LoggerFactory.getLogger(ExamStatusScanJob.class);
	
//	@Autowired
	private ExamAutoer examAutoer = new ExamAutoerImpl();
	
//	@Autowired
	private ExamDao examDao = new ExamDaoImpl();
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("-----------------job start ........");
		//监控是否有考试要开启
		Tuple isStart = examAutoer.getRecentBeginExam();
		long startTime = (long) isStart.getScore();
		//如果时间到了，就开启考试
		if (startTime + 60000 >= new Date().getTime()) {
			int examId = Integer.parseInt(isStart.getElement());
			try {
				examDao.setState(examId, "begined");
				examAutoer.dequeueBegin(examId);
			} catch (SQLException e) {
				e.printStackTrace();
				log.error("警告：自动开启考试失败......\n" + ExceptionUtil.getStackTrace(e));
			}
		}
		
		//监控是否有考试要结束
		Tuple isClose = examAutoer.getRecentCloseExam();
		long closeTime = (long) isClose.getScore();
		//如果时间到了，就开启考试
		if (closeTime >= new Date().getTime()) {
			int examId = Integer.parseInt(isClose.getElement());
			try {
				examDao.setState(examId, "closed");
				examAutoer.dequeueClose(examId);
			} catch (SQLException e) {
				e.printStackTrace();
				log.error("警告：自动结束考试失败......\n" + ExceptionUtil.getStackTrace(e));
			}
		}
	}

}
