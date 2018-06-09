package henu.web.lisener;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import henu.quartz.ExamStatusScanner;

/**
 * @Describtion: (执行考试任务调度). <br/> 
 * @date: 2018年6月9日 下午12:46:45 <br/> 
 * @author Beats <br/> 
 * @version v1.0 <br/>
 * @since JDK 1.8
 */
@Component
public class SpringContextLisener implements InitializingBean {

	@Autowired ExamStatusScanner examStatusScanner;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		//启动
		examStatusScanner.run();
	}

}
