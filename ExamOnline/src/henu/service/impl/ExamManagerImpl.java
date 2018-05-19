package henu.service.impl;

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
	public ResultModel createExam(Exam exam) {
		
		return null;
	}

	@Override
	public ResultModel editExam(Exam exam) {
		
		return null;
	}

	@Override
	public ResultModel setExamState(String id, String status) {
		
		return null;
	}

	@Override
	public ResultModel importQues(String id, List<Question> ques) {
		
		return null;
	}

	@Override
	public ResultModel getQues(String id) {
		
		return null;
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

}
