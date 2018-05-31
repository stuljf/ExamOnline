package henu.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import henu.service.ExamManager;

@RunWith(SpringJUnit4ClassRunner.class)
//加载配置文件
@ContextConfiguration(locations="classpath:spring.xml")
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)  
public class TestExamManager {

	@Resource
	private ExamManager examManager;
	
	@Test
	public void testImportStudent() {
//		examManager.importStudents(2, new File("d:/1.xlsx"));
	}
	
}
