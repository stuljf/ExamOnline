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
		
		//监控是否有考试要开启
		System.out.println("startExam...");
		startExam();
		
		//监控是否有考试要结束
		System.out.println("closeExam...");
		closeExam();
		
/*	可以执行线程内的	
 * System.out.println("sdhfkjdsfdskjf");
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("job's  thread ....");
			}
		}).start();*/
	}

	private void closeExam() {
		//获取最近一个将要关闭的考试
		Tuple isClose = examAutoer.getRecentCloseExam();
		System.out.println(isClose);
		//如果不为空
		if (isClose != null) {
			//获取考试ID和结束时间
			long closeTime = (long) isClose.getScore();
			String examIdInt = isClose.getElement();
			System.out.println(closeTime);
			System.out.println(examIdInt);
			//如果时间到了，就结束考试//以下的输出和日志都不会打印，
			long now = new Date().getTime();
			if (now >= closeTime) {
				int examId = Integer.parseInt(examIdInt);
				System.out.println(examId);
				try {
					examDao.setState(examId, "closed");
					log.error("考试结束。。。" + examId);
					System.out.println("exam is closing....");
					examAutoer.dequeueClose(examId);
					log.error("考试出队。。。" + examId);
					System.out.println("exam is dequeuing...");
					//执行考试结束后的逻辑，比如保存并上传答案
					log.error("开始保存考试成绩...");
					System.out.println("preparing to save the scores...");
					new Thread(new ExamDisposer(examId)).start();
					System.out.println("upload the scores success...");
					log.error("线程正在运行中...");
				} catch (SQLException e) {
					e.printStackTrace();
					log.error("警告：自动结束考试失败......\n" + ExceptionUtil.getStackTrace(e));
				}
			}
		}
	}

	private void startExam() {
		Tuple isStart = examAutoer.getRecentBeginExam();
		if (isStart != null) { 

			long startTime = (long) isStart.getScore();
			String examIdInt = isStart.getElement();
			//如果时间到了，就开启考试
			long now = new Date().getTime();
			if (now + 60000 >= startTime) {
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
	}

}
