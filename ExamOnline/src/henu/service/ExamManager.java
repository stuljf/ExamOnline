package henu.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import henu.entity.Exam;
import henu.entity.Question;
import henu.entity.Student;
import henu.util.PageBean;
import henu.util.ResultModel;

public interface ExamManager {

	/**
	 * @Description:(创建考试). <br/> 
	 * @param exam
	 * @return
	 * @throws SQLException 
	 */
	ResultModel createExam(Exam exam) throws SQLException;

	/**
	 * @Description:(修改考试信息). <br/> 
	 * @param exam
	 * @return
	 * @throws SQLException 
	 */
	ResultModel editExam(Exam exam) throws SQLException;

	/**
	 * @Description:(修改考试状态). <br/> 
	 * @param id
	 * @param status 开始，进行中，取消，结束
	 * @param timeLimit 提前多少毫秒可以开启考试
	 * @return
	 * @throws SQLException 
	 */
	ResultModel setExamState(int id, String status) throws SQLException;
	
	/**
	 * @Description:(获得考试信息). <br/> 
	 */
	Exam getExamById(int id);

	/**
	 * @Description:(根据教师和考试状态查询). <br/> 
	 * @param t_id
	 * @param state
	 * @return
	 * @throws SQLException 
	 */
	List<Exam> queryExamByTeacher(String t_id, String state) throws SQLException;
	
	/**
	 * @Description:(导入 试题). <br/> 
	 * @param ques
	 * @return
	 * @throws SQLException 
	 */
	ResultModel importQues(List<Question> ques) throws SQLException;

	/**
	 * @Description:(获取试题). <br/> 
	 * @param examId
	 * @return
	 * @throws SQLException 
	 */
	List<Question> getQues(int examId) throws SQLException;


	/**
	 * @Description:(导入学生名单，批量导入). <br/> 
	 * @param id
	 * @param stus
	 * @return
	 */
	ResultModel importStudents(int id, MultipartFile multipartFile);

	/**
	 * @Description:(导入学生名单，新增). <br/> 
	 * @param id
	 * @param stu
	 * @return
	 */
	ResultModel addStudent(int id, Student stu);

	/**
	 * @Description:(学生名单，删除). <br/> 
	 * @param id
	 * @param s_id
	 * @return
	 */
	ResultModel removeStudent(int id, String s_id);

	/**
	 * @Description:(学生名单，修改). <br/> 
	 * @param id
	 * @param stu
	 * @return
	 */
	ResultModel updateStudent(int id, Student stu);

	/**
	 * @Description:(导入学生名单，查询). <br/> 
	 * @param examId
	 * @param bean
	 * @throws SQLException 
	 */
	void queryStudent(int examId, PageBean<Student> bean);
	
	/**
	 * @Description:(获取当前考试的所有学生，不分页). <br/> 
	 * @param examId
	 * @return
	 */
	List<Student> queryStudent(int examId);

	/**
	 * @Description:(学生名单，关键字查询). <br/> 
	 * @param id
	 * @param bean
	 * @return
	 */
	ResultModel queryStudentByKey(String id, String key);

	/**
	 * @Description:(IP解绑). <br/> 
	 * @param s_id
	 * @param s_name 
	 * @return
	 */
	ResultModel unbindIP(String s_id, String s_name);

	/**
	 * @Description:(手动开启一场考试). <br/> 
	 * @param eId
	 * @param string
	 * @param timeLimit
	 * @return
	 */
	ResultModel startExam(Integer eId, String string, long timeLimit);

	String getExamState(int examId);
	
	long getStudentCount(int examId, String type);
}
