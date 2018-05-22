package henu.service;

import java.sql.SQLException;
import java.util.List;

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
	 * @return
	 * @throws SQLException 
	 */
	ResultModel setExamState(String id, String status) throws SQLException;

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
	 * @param id
	 * @param ques
	 * @return
	 */
	ResultModel importQues(String id, List<Question> ques);

	/**
	 * @Description:(获取试题). <br/> 
	 * @param id
	 * @return
	 */
	ResultModel getQues(int id);


	/**
	 * @Description:(导入学生名单，批量导入). <br/> 
	 * @param id
	 * @param stus
	 * @return
	 */
	ResultModel importStudents(String id, List<Student> stus);

	/**
	 * @Description:(导入学生名单，新增). <br/> 
	 * @param id
	 * @param stu
	 * @return
	 */
	ResultModel addStudent(String id, Student stu);

	/**
	 * @Description:(学生名单，删除). <br/> 
	 * @param id
	 * @param s_id
	 * @return
	 */
	ResultModel removeStudent(String id, String s_id);

	/**
	 * @Description:(学生名单，修改). <br/> 
	 * @param id
	 * @param stu
	 * @return
	 */
	ResultModel updateStudent(String id, Student stu);

	/**
	 * @Description:(导入学生名单，查询). <br/> 
	 * @param id
	 * @param bean
	 * @return
	 */
	ResultModel queryStudent(String id, PageBean<Student> bean);

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
	 * @return
	 */
	ResultModel unbindIP(String s_id);
}
