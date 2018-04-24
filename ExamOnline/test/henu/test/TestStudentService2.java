package henu.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import henu.entity.Student;
import henu.service.StudentService;
import henu.util.ResultModel;

//作为Spring Test的类
@RunWith(SpringJUnit4ClassRunner.class)
//加载配置文件
@ContextConfiguration(locations="classpath:spring.xml")
public class TestStudentService2 {
	
	//依赖注入
	@Resource 
	private StudentService service;
	
	//测试
	@Test
	public void testLogin2(){
		//开始测试
		Student s = new Student();
		s.setId("1510121111");
		s.setName("Lucy");
		
		ResultModel login = service.login(s);
		System.out.println(login.getStatus() + ":" + ((Student)login.getData()).getClazz());
	}
	
}
