package henu.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import henu.dao.ExamDao;
import henu.entity.Exam;
import henu.entity.Question;
import henu.entity.Student;
import henu.service.ExamManager;
import henu.util.PageBean;
import henu.util.ResultModel;

@Service //业务逻辑层注解
public class ExamManagerImpl implements ExamManager {

	@Resource
	private ExamDao examDao;

	@Override
	public ResultModel createExam(Exam exam) throws SQLException {
		//插入
		examDao.save(exam);
		//获取自增主键
		int id = examDao.getLastInsertID();
		//设置id
		exam.setId(id);
		return ResultModel.ok(exam);
	}

	@Override
	public ResultModel editExam(Exam exam) throws SQLException {
		//获取旧数据
		Exam t = examDao.queryExamsById(exam.getId());
		//填充数据
		exam.setState(t.getState());
		exam.setT_id(t.getT_id());
		//修改数据
		examDao.modify(exam);
		return ResultModel.ok();
	}

	@Override
	public ResultModel setExamState(String id, String status) throws SQLException {
		examDao.setState(id, status);
		return ResultModel.ok();
	}

	@Override
	public ResultModel importQues(String id, List<Question> ques) throws SQLException {
		
		return null;
	}

	@Override
	public List<Question> getQues(int id) throws SQLException {
		List<Question> ques = examDao.getQues(id);
		return ques;
	}

	@Override
	public ResultModel importStudents(String id, List<Student> stus) {
		
		return null;
	}

	@Override
	public ResultModel addStudent(String id, Student stu) {
		
		return null;
	}

	@Override
	public ResultModel removeStudent(String id, String s_id) {
		
		return null;
	}

	@Override
	public ResultModel updateStudent(String id, Student stu) {
		
		return null;
	}

	@Override
	public ResultModel queryStudent(String id, PageBean<Student> bean) {
		
		return null;
	}

	@Override
	public ResultModel queryStudentByKey(String id, String key) {
		
		return null;
	}

	@Override
	public ResultModel unbindIP(String s_id) {
		
		return null;
	}

	@Override
	public List<Exam> queryExamByTeacher(String t_id, String state) throws SQLException {
		return examDao.queryExamsByTeacher(t_id, state);
	}

}
