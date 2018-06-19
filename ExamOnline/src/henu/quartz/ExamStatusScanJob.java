package henu.quartz;

import java.sql.SQLException;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import henu.service.ExamAutoer;
import henu.service.ExamDisposer;
import henu.service.ExamManager;
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

	private ExamManager examManager;

	public ExamStatusScanJob() {
		examAutoer = (ExamAutoer) SpringUtil.getBean("examAutoerImpl");
		examManager = (ExamManager) SpringUtil.getBean("examManagerImpl");
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		//监控是否有考试要开启
		startExam();

		//监控是否有考试要结束
		closeExam();
	}

	private void startExam() {
		//获取最近一个将要开始的考试
		Tuple isStart = examAutoer.getRecentBeginExam();
		//不为空就检查时间是否满足
		if (isStart != null) { 
			long startTime = (long) isStart.getScore();
			//System.out.println("start:" + startTime);
			String examIdInt = isStart.getElement();
			//如果时间到了，就开启考试
			long now = new Date().getTime();
			//System.out.println("now  :" + now);
			if (now + 60000 >= startTime) {
				int examId = Integer.parseInt(examIdInt);
				//System.out.println("exam id:" + examId);
				try {
					examManager.setExamState(examId, "begined");
					//System.out.println("exam began......");
				} catch (SQLException e) {
					e.printStackTrace();
					log.error("警告：自动开启考试失败......\n" + ExceptionUtil.getStackTrace(e));
				}
			}
		}
	}

	private void closeExam() {
		//获取最近一个将要关闭的考试
		Tuple isClose = examAutoer.getRecentCloseExam();
		//如果不为空
		if (isClose != null) {
			//获取考试ID和结束时间
			long closeTime = (long) isClose.getScore();
			//System.err.println("close:" + closeTime);
			String examIdInt = isClose.getElement();
			//如果时间到了，就结束考试//以下的输出和日志都不会打印，
			long now = new Date().getTime();
			//System.err.println("now  :" + now);
			if (now >= closeTime) {
				int examId = Integer.parseInt(examIdInt);
				//System.err.println("exam id:" + examId);
				try {
					//执行考试结束后的逻辑，比如保存并上传答案
					//System.out.println("closing......");
					examManager.setExamState(examId, "closed");
					new Thread(new ExamDisposer(examId)).start();
					//System.out.println("closed......");
				} catch (SQLException e) {
					e.printStackTrace();
					log.error("警告：自动结束考试失败......\n" + ExceptionUtil.getStackTrace(e));
				}
			}
		}
	}

}
