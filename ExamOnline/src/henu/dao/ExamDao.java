package henu.dao;

import java.sql.SQLException;
import java.util.List;

import henu.entity.Exam;
import henu.entity.Question;
import henu.util.PageBean;

public interface ExamDao {
	
	/**
	 * save:(新建一场考试)
	 * @param exam
	 * @throws SQLException
	 */
	void save(Exam exam) throws SQLException;
	
	/**
	 * remove:(移除一场考试)
	 * @param id
	 * @throws SQLException
	 */
	void remove(Integer id) throws SQLException;
	
	/**
	 * modify:(修改考试信息)
	 * @param exam
	 * @throws SQLException
	 */
	void modify(Exam exam) throws SQLException;
	
	/**
	 * queryExamsByTeacher:(查找教师创建的考试)
	 * @param teacherId
	 * @return
	 * @throws SQLException
	 */
	List<Exam> queryExamsByTeacher(String teacherId) throws SQLException;
	
	/**
	 * queryExamsByStudent:(根据考试号查找考试)
	 * @param examId
	 * @return
	 * @throws SQLException
	 */
	Exam queryExamsById(String examId) throws SQLException;
	
	/**
	 * setStatus:(设置考试状态)
	 * @param id
	 * @param status
	 * @throws SQLException
	 */
	void setState(String id, String status) throws SQLException;
	
	/**
	 * @Description:(根据考试状态查询考试列表). <br/> 
	 * @param status
	 * @return
	 * @throws SQLException
	 */
	void getExamsByState(PageBean<Exam> bean, String status) throws SQLException;
	
	/**
	 * getQues:(获取试卷的试题)
	 * @param id
	 * @return List<Question>
	 * @throws SQLException
	 */
	List<Question> getQues(String id) throws SQLException;
	
	/**
	 * importQues:(上传试卷的试题)
	 * @param examId
	 * @param question
	 * @throws SQLException
	 */
	void importQues(String id, Question ques) throws SQLException;
	
	
}
