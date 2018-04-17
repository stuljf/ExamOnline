package henu.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import henu.entity.Student;
import henu.service.StudentService;
import henu.util.ResultModel;

public class TestStudentService {

	
//	@Resource 
//	private StudentDao dao;
	
	@Test
	public void testLogin(){
		//加载配置文件
		ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");
		//获取对象
		StudentService service = ac.getBean(StudentService.class);
		//开始测试
		Student s = new Student();
		s.setId("1510121111");
		s.setName("Lucy");
		
		ResultModel login = service.login(s);
		System.out.println(login.getStatus() + ":" + ((Student)login.getData()).getClazz());
	}
	
}
