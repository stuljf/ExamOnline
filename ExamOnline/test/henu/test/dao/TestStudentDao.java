package henu.test.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import henu.dao.StudentDao;
import henu.entity.Student;

/**
 * @ClassName: TestStudentDao <br/> 
 * @Describtion: (StudentDao测试). <br/> 
 * @date: 2018年4月24日 下午4:35:28 <br/> 
 * @author Beats <br/> 
 * @version v1.0 <br/>
 * @since JDK 1.8
 */
//作为Spring Test的类
@RunWith(SpringJUnit4ClassRunner.class)
//加载配置文件
@ContextConfiguration(locations="classpath:spring.xml")
public class TestStudentDao {

	//依赖注入
	@Resource 
	private StudentDao dao;

	@Test
	public void testRemove() {
		try {
			dao.remove("1510121175");
			Student s = dao.queryStudentById("1510121175");
			assertNull(s);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testQueryStudentById() {
		try {
			Student s = dao.queryStudentById("1510121175");
			assertEquals(s.getName(), "ljf");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//测试保存
	@Test
	public void testSave(){
		Student s = new Student();
		s.setId("1510121175");
		s.setName("ljf");
		s.setClazz("15-2");
		s.setE_id(1);
		try {
			dao.save(s);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
