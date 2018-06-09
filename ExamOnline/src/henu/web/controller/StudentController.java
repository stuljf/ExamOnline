package henu.web.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import henu.dao.ExamDao;
import henu.entity.Exam;
import henu.entity.Question;
import henu.entity.Student;
import henu.service.ExamJudger;
import henu.service.StudentService;
import henu.util.ExceptionUtil;
import henu.util.RequestModel;
import henu.util.ResultModel;

@Controller
@RequestMapping("/student")
public class StudentController {

	private Logger log = LoggerFactory.getLogger(StudentController.class);

	@Autowired
	private StudentService studentService;

	@Autowired
	private ExamDao examDao;

	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private ExamJudger examJudger;

	@RequestMapping(value="/login", method=RequestMethod.POST)
	@ResponseBody
	public ResultModel login(Student student, Model model, HttpServletRequest request) {
		try {
			log.error(student.getId() + student.getName());
			//登陆
			ResultModel res = studentService.login(student);

			if (res.getStatus() == 200) {
				//保存session
				HttpSession session = request.getSession();
				session.setAttribute("student", res.getData());
			}
			//返回结果
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(ExceptionUtil.getStackTrace(e));
			return ResultModel.build(500, "系统错误！");
		}
	}

	@RequestMapping("/logout")
	public String logout(String sId, HttpServletRequest request) {
		try {
			//session
			HttpSession session = request.getSession();
			session.removeAttribute("student");

			//返回结果
			return "login";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value="/exam/list")
	@ResponseBody
	public List<Exam> getExamList(String sId) {
		//判空
		if (sId == null) return null;

		try {
			ResultModel res = studentService.queryExams(sId);
			if(res.getStatus()==200) {
				return res.getListData(Exam.class);
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value="/exam/start")
	public String examStart(Integer eId, Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Student student=(Student) session.getAttribute("student");

		//String ip=IPUtil.getRealIP(request);
		//student.setIp(ip);
		student.setE_id(eId);

		ResultModel res = studentService.bindIp(student);

		if(res.getStatus()==200) {
			res = studentService.displayQuestion(eId);
			if(res.getStatus()==200) {
				List<Question> ques = res.getListData(Question.class);
				//视图渲染
				model.addAttribute("ques", ques);
				model.addAttribute("examId", eId);
				//返回视图
				return "exam";
			} else {
				return "error";
			}
		}else {
			model.addAttribute("bindIp", res.getMsg());
			return "student";
		}
	}

	//提交答案
	@RequestMapping(value="/exam/submit")
	@ResponseBody
	public ResultModel examSubmit(Integer examId, String studentId, RequestModel bean) {
		if (studentId == null || bean == null) {
			return ResultModel.build(400, "参数不能为空！");
		}
		
		try {
			//提交答案后开始储存和改卷
			//存储到ftp
			studentService.saveAsnwers(examId, studentId, bean.getQuestions());
			//改卷
			int score = examJudger.judge(examId, bean.getQuestions());
			//结果临时存储到application
			servletContext.setAttribute("score:" + examId + ":" + studentId, score);
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(ExceptionUtil.getStackTrace(e));
			return ResultModel.build(500, "系统错误，改卷失败！");
		}
		
		return ResultModel.ok();
	}
	
	//查看老师发布的公告
	@RequestMapping("/exam/broadcast/list/{examId}")
	@ResponseBody
	public ResultModel listBroadcast(@PathVariable String examId) {
		//从application中获取
		String publish = (String) servletContext.getAttribute("publish:" + examId);
		return ResultModel.ok(publish);
	}
}
