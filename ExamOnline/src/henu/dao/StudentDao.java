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
	 * @param id
	 * @throws SQLException 
	 */
	void remove(String id, int examId) throws SQLException;
	
	/**
	 * getStudentById:(get Student info by student's id). <br/> 
	 * @param id
	 * @param examId
	 * @return Student
	 * @throws SQLException 
	 */
	List<Student> queryStudentById(String id) throws SQLException;;
	
	/**
	 * @Description:(get Student info by student's id and exam's id). <br/> 
	 * @param id
	 * @param examId
	 * @return
	 * @throws SQLException
	 */
	Student queryStudentByIdAndExam(String id, int examId) throws SQLException;
	
	/**
	 * modify:(修改学生信息). <br/> 
	 * @param student void
	 * @throws SQLException 
	 */
	void modify(Student student) throws SQLException;
	
	/**
	 * queryAll:(分页查询所有学生信息). <br/> 
	 * @param stus 分页Bean，封装学生信息
	 * @return List<Student>
	 * @throws SQLException 
	 */
	void queryAll(int examId, PageBean<Student> stus) throws SQLException;
	/**
	 * @Description:(不分页). <br/> 
	 * @param examId
	 * @return
	 * @throws SQLException 
	 */
	List<Student> queryAll(int examId) throws SQLException;
	
	/**
	 * studentExists:(查询是否存在该学生). <br/> 
	 * @param student
	 * @return Student 如果存在就返回所有信息，不存在返回null
	 * @throws SQLException 
	 */
	List<Student> studentExists(Student student) throws SQLException;
	
	/**
	 * modifyIp:(绑定Ip)
	 * @param ip
	 * @throws SQLException
	 */
	void modifyIp(Student student) throws SQLException;
	
	/**
	 * query(查询一个学生通过学号和考试号)
	 * @param student
	 * @return
	 * @throws SQLException
	 */
	Student query(Student student) throws SQLException;
	
	/**
	 * unbindIp(解除IP绑定)
	 * @param id
	 * @throws SQLException
	 */
	public void unbindIp(String id) throws SQLException;


}
