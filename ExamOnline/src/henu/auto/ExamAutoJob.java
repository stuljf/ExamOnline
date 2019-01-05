package henu.auto;

import java.sql.SQLException;
import java.util.*;
import henu.service.ExamManager;
import henu.util.SpringUtil;

public class ExamAutoJob extends TimerTask{

	private ExamQueue examQueue;
	private ExamManager examManager;
	private int interval;
	
	public ExamAutoJob(int time) {
		interval=time/2+1000;
		examQueue=(ExamQueue) SpringUtil.getBean("examQueue");
		examManager=(ExamManager) SpringUtil.getBean("examManagerImpl");
	}
	
	@Override
	public void run() {
		startExam();
		endExam();
	}

	private void startExam() {
		Map.Entry<Integer, Date> exam = examQueue.getRecentBegin();
		if(exam != null) {
			long time = exam.getValue().getTime();
			long now = new Date().getTime();
			if(time <= now+interval && time >= now-interval) {
				try {
					examManager.setExamState(exam.getKey(), "begined");
				} catch (SQLException e) {
					System.err.println("自动开始考试失败："+exam.getKey().toString()+exam.getValue().toString());
					e.printStackTrace();
				}
			}
		}
	}

	private void endExam() {
		Map.Entry<Integer, Date> exam = examQueue.getRecentClose();
		if(exam != null) {
			long time = exam.getValue().getTime();
			long now = new Date().getTime();
			if(time <= now+interval && time >= now-interval) {
				try {
					examManager.setExamState(exam.getKey(), "closed");
					ExamSave examSave=new ExamSave(exam.getKey());
					new Thread(examSave).start();
				} catch (SQLException e) {
					System.err.println("自动取消考试失败："+exam.getKey().toString()+exam.getValue().toString());
					e.printStackTrace();
				}
			}
		}
	}
}
