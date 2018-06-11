package henu.quartz;

import java.sql.SQLException;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import henu.dao.ExamDao;
import henu.service.ExamAutoer;
import henu.service.ExamDisposer;
import henu.util.ExceptionUtil;
import henu.util.SpringUtil;
import redis.clients.jedis.Tuple;

/**
 * @Describtion: (考试状态扫描，用于自动开启或关闭考试). <br/> 
 * @date: 2018年6月5日 下午6:02:37 <br/> 
 * @author Beats <br/> 
 * @version v1.0 <br/>
 * @since JDK 1.8
 */
public class ExamStatusScanJob implements Job {

	private Logger log = LoggerFactory.getLogger(ExamStatusScanJob.class);

	private ExamAutoer examAutoer;

	private ExamDao examDao;
	
	public ExamStatusScanJob() {
		examAutoer = (ExamAutoer) SpringUtil.getBean("examAutoerImpl");
		examDao = (ExamDao) SpringUtil.getBean("examDaoImpl");
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		//		System.out.println("-----------------job start ........");
		//监控是否有考试要开启
		Tuple isStart = examAutoer.getRecentBeginExam();

		if (isStart != null) { 

			long startTime = (long) isStart.getScore();
			String examIdInt = isStart.getElement();
			//如果时间到了，就开启考试
			if (new Date().getTime() + 60000 >= startTime) {
				int examId = Integer.parseInt(examIdInt);
				try {
					examDao.setState(examId, "begined");
					examAutoer.dequeueBegin(examId);
				} catch (SQLException e) {
					e.printStackTrace();
					log.error("警告：自动开启考试失败......\n" + ExceptionUtil.getStackTrace(e));
				}
			}
		}
		//监控是否有考试要结束
		Tuple isClose = examAutoer.getRecentCloseExam();
		if (isClose != null) {
			long closeTime = (long) isClose.getScore();
			String examIdInt2 = isClose.getElement();
			//如果时间到了，就开启考试
			if (new Date().getTime() >= closeTime) {
				int examId = Integer.parseInt(examIdInt2);
				try {
					examDao.setState(examId, "closed");
					examAutoer.dequeueClose(examId);
					//执行考试结束后的逻辑，比如保存并上传答案
					new Thread(new ExamDisposer(examId)).start();
				} catch (SQLException e) {
					e.printStackTrace();
					log.error("警告：自动结束考试失败......\n" + ExceptionUtil.getStackTrace(e));
				}
			}
		}
	}

}
