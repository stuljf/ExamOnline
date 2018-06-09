package henu.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import henu.dao.ExamDao;
import henu.dao.StudentDao;
import henu.entity.Exam;
import henu.entity.Question;
import henu.entity.Student;
import henu.service.StudentService;
import henu.util.ExceptionUtil;
import henu.util.ResultModel;

@Service //业务逻辑层注解
public class StudentServiceImpl implements StudentService {

	private Logger log = LoggerFactory.getLogger(StudentService.class);
	
	@Resource
	private StudentDao studentDao;
	
	@Resource
	private ExamDao examDao;
	
	@Autowired
	private ServletContext servletContext;
	
	@Override
	public ResultModel login(Student student) {
		try {
			List<Student> exists = studentDao.studentExists(student);
			if (!exists.isEmpty()) {
				return ResultModel.ok(exists.get(0));
			} else {
				return ResultModel.build(400, "请核对学号姓名");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(ExceptionUtil.getStackTrace(e));
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
	public ResultModel displayQuestion(int eId) {
		try {
			List<Question> ques = examDao.getQues(eId);
			return ResultModel.ok(ques);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResultModel.build(500, "获取试卷错误");
		}
	}

	@Override
	public ResultModel saveAsnwers(Student stu, List<String> answers) {
		
		return null;
	}

	@Override
	public ResultModel bindIp(Student student) {
		try {
			//判断是否在考试开始允许时间内开始
			Exam exam = examDao.queryExamsById(student.getE_id());
			String timeLimitS = (String) servletContext.getAttribute("timeLimit");
			long timeLimit = Integer.parseInt(timeLimitS) * 60 * 1000;
			if (new Date().getTime()  > exam.getStarttime().getTime() + timeLimit) {
				return ResultModel.build(400, "考试已开始15分钟，无法进入！");
			}
			//判断IP是否已绑定
			Student stu=studentDao.query(student);
			if(stu.getIp().isEmpty() || stu.getIp().trim().equals("0.0.0.0")) {
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
