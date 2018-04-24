package henu.dao;

import java.sql.SQLException;
import java.util.List;

import henu.entity.Student;
import henu.util.PageBean;

/**
 * @ClassName: StudentDao <br/> 
 * @Describtion: 针对Student的一系列CURD. <br/> 
 * @date: 2018年4月17日 下午3:37:45 <br/> 
 * @author Beats <br/> 
 * @version v1.0
 */
public interface StudentDao {
	
	/**
	 * save:(倒入学生信息，包含Exam的id). <br/> 
	 * @param student 
	 * @throws SQLException 
	 */
	void save(Student student) throws SQLException;
	
	/**
	 * remove:(根据学生id和考试id移除学生名单). <br/> 
	 * @param id void
	 * @throws SQLException 
	 */
	void remove(String id) throws SQLException;
	
	/**
	 * getStudentById:(get Student info by student's id). <br/> 
	 * @param id
	 * @return Student
	 * @throws SQLException 
	 */
	Student queryStudentById(String id) throws SQLException;
	
	/**
	 * modify:(修改学生信息). <br/> 
	 * @param student void
	 * @throws SQLException 
	 */
	void modify(Student student) throws SQLException;
	
	/**
	 * queryAll:(分页查询所有学生信息). <br/> 
	 * @param stus
	 * @return List<Student>
	 * @throws SQLException 
	 */
	List<Student> queryAll(PageBean<Student> stus) throws SQLException;
	
	/**
	 * studentExists:(查询是否存在该学生). <br/> 
	 * @param student
	 * @return Student 如果存在就返回所有信息，不存在返回null
	 * @throws SQLException 
	 */
	Student studentExists(Student student) throws SQLException;
}
