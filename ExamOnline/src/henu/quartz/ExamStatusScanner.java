package henu.quartz;

import javax.annotation.Resource;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import henu.util.ExceptionUtil;

@Component
public class ExamStatusScanner extends Thread {

	private Logger log = LoggerFactory.getLogger(ExamStatusScanner.class);
	
	@Resource
	private SchedulerFactory stdSchedulerFactory;
	
	@Value("${crontime}")
	private String crontime;
	
	@Override
	public void run() {
		
		try {
			//调度器
			Scheduler scheduler = stdSchedulerFactory.getScheduler();

			//创建具体任务，绑定Job实现类
			JobDetail job= JobBuilder.newJob(ExamStatusScanJob.class)
			        .withIdentity("examStatusScanner")
			        .build();

			//创建触发规则
			/*Trigger trigger = TriggerBuilder.newTrigger()
			        //触发器描述
			        .withIdentity("myTrigger", "triggerGroup")
			        //重复规则
			        .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(1)
			        //.withRepeatCount(10)
			        )
			        //开始时间
			        .startNow()
			        .build();*/
			//支持表达式的触发器
			Trigger trigger = TriggerBuilder.newTrigger()
			        .withIdentity("examStatusScannerTrigger")
			        .withSchedule(CronScheduleBuilder.cronSchedule(crontime))
			        .startNow()
			        .build();


			//将触发器绑定到作业身上
			scheduler.scheduleJob(job, trigger);

			//启动调度
			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
			log.error("自动考试管理调度器无法正常运行......\n" + ExceptionUtil.getStackTrace(e));
		}
	}
}
