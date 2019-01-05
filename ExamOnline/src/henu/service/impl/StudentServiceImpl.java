package henu.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import henu.auto.ExamScore;
import henu.dao.ExamDao;
import henu.dao.StudentDao;
import henu.entity.Exam;
import henu.entity.Question;
import henu.entity.Student;
import henu.service.StudentService;
import henu.util.ExcelWriter;
import henu.util.ExceptionUtil;
import henu.util.ResultModel;

@Service //业务逻辑层注解
public class StudentServiceImpl implements StudentService {

	private Logger log = LoggerFactory.getLogger(StudentService.class);
	
	@Resource
	private StudentDao studentDao;
	
	@Resource
	private ExamDao examDao;
	
	@Value("${ftp.url}")
	private String ftp_url;
	@Value("${ftp.port}")
	private int ftp_port;
	@Value("${ftp.username}")
	private String ftp_username;
	@Value("${ftp.passwd}")
	private String ftp_passwd;
	
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private ExamScore examScore;
	
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
	public ResultModel bindIp(Student student) {
		try {
			//判断IP是否已绑定
			Student stu=studentDao.query(student);
			if(stu.getIp().isEmpty() || stu.getIp().trim().equals("0.0.0.0")) {
				//判断是否在考试开始允许时间内开始
				Exam exam = examDao.queryExamsById(student.getE_id());
				String timeLimitS = (String) servletContext.getAttribute("timeLimit");
				long timeLimit = Integer.parseInt(timeLimitS) * 60 * 1000;
				if (new Date().getTime()  > exam.getStarttime().getTime() + timeLimit) {
					return ResultModel.build(400, "考试已开始15分钟，无法进入！");
				}
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

	@Override
	public ResultModel saveAsnwers(int examId, String studentId, List<Question> ques) throws SQLException {
		//获取考试详情
		Exam exam = examDao.queryExamsById(examId);
		if(exam.getState().equals("closed")) {
			return ResultModel.build(200, "考试已经结束，提交答案失败");
		}
		//获取学生详情
		Student stu = studentDao.queryStudentByIdAndExam(studentId, examId);
		
		ExcelWriter writer = new ExcelWriter();
		writer.write(0, 0, "题号");
		writer.write(0, 1, "答案");
		writer.write(0, 2, "得分");
		
		//统计总成绩
		int count=0;
		List<Question> realQues = examDao.getQues(examId);
		Map<Integer, String> answers = new HashMap<Integer, String>();
		for(Question q:ques) {
			answers.put(q.getNumber(), q.getAnswer());
		}
		
		//将答案和得分保存到excel
		for (Question question : realQues) {
			Integer num=question.getNumber();
			int score;
			if(question.getAnswer().equals(answers.get(num))) {
				score=question.getScore();
				count+=score;
			}else {
				score=0;
			}
			
			writer.write(num, 0, num.toString());
			writer.write(num, 1, answers.get(num));
			writer.write(num, 2, Integer.toString(score));
			
		}
		//总分保存到成绩缓存中
		examScore.set(examId+":"+studentId, count+"");
		
		//保存excel
		String path=servletContext.getRealPath("exam")+"/"+examId+"-"+exam.getSubject();
		File dir = new File(path);
		if(!dir.exists()) {
			dir.mkdir();
		}
		String name="/"+stu.getId()+"-"+stu.getName();
		
		try {
			FileOutputStream out = new FileOutputStream(path+name);
			writer.saveToFile(out);
			writer.close();
			out.close();
			return ResultModel.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultModel.build(400, "保存答案失败！");
		}
		
		
	}

}
