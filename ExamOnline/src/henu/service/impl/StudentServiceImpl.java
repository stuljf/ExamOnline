package henu.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import henu.dao.ExamDao;
import henu.dao.StudentDao;
import henu.entity.Exam;
import henu.entity.Question;
import henu.entity.Student;
import henu.service.StudentService;
import henu.util.ResultModel;

@Service //业务逻辑层注解
public class StudentServiceImpl implements StudentService {

	@Resource
	private StudentDao studentDao;
	private ExamDao examDao;
	
	@Override
	public ResultModel login(Student student) {
		try {
			List<Student> exists = studentDao.studentExists(student);
			if (!exists.isEmpty()) {
				return ResultModel.ok(student);
			} else {
				return ResultModel.build(400, "请核对学号姓名");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return ResultModel.build(500, "系统错误！");
		}
	}

	@Override
	public ResultModel queryExams(String id) {
		Exam exam;
		List<Exam> exams=new ArrayList<Exam>();
		try {
			List<Student> students=studentDao.queryStudentById(id);
			for (Student s : students) {
				exam=examDao.queryExamsById(s.getE_id());
				if(!exams.add(exam))
					return ResultModel.build(400, "查找考试错误");
			}
			return ResultModel.ok(exams);
		}catch(SQLException e) {
			return ResultModel.build(500, "系统错误");
		}
	}

	@Override
	public ResultModel displayQuestion(String eId) {
		try {
			int id=Integer.parseInt(eId);
			List<Question> ques = examDao.getQues(id);
			return ResultModel.ok(ques);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResultModel.build(500, "系统错误");
		}
	}

	@Override
	public ResultModel saveAsnwers(Student stu, List<String> answers) {
		
		return null;
	}

	@Override
	public ResultModel bindIp(Student student) {
		try {
			Student stu=studentDao.query(student);
			if(stu.getIp().isEmpty()) {
				studentDao.modifyIp(student);
				return ResultModel.ok();
			}else {
				if(stu.getIp().equals(student.getIp())) {
					return ResultModel.ok();
				}else {
					return ResultModel.build(400, "你已经在其他地方登陆");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResultModel.build(500, "系统错误");
		}
	}

}
