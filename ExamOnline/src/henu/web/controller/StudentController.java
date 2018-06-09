package henu.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import henu.entity.Exam;
import henu.entity.Question;
import henu.entity.Student;
import henu.service.StudentService;
import henu.util.ExceptionUtil;
import henu.util.IPUtils;
import henu.util.ResultModel;

@Controller
@RequestMapping("/student")
public class StudentController {

	private Logger log = LoggerFactory.getLogger(StudentController.class);
	
	@Resource
	private StudentService studentService;
	
	@Resource
	private ServletContext servletContext;
	
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
	public String login(String sId, HttpServletRequest request) {
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
		
//		log.error("Proxy?WL-Proxy-Client-IP----" + request.getHeader("WL-Proxy-Client-IP"));
//		log.error("Proxy?Proxy-Client-IP----" + request.getHeader("Proxy-Client-IP"));
//		log.error("Proxy?X-Forwarded-For----" + request.getHeader("X-Forwarded-For"));
//		log.error("Proxy?WL-Proxy-Client-Keysize----" + request.getHeader("WL-Proxy-Client-Keysize"));
//		log.error("Proxy?WL-Proxy-Client-Secretkeysize----" + request.getHeader("WL-Proxy-Client-Secretkeysize"));
//		log.error("Proxy?X-WebLogic-Request-ClusterInfo----" + request.getHeader("X-WebLogic-Request-ClusterInfo"));
//		log.error("Proxy?X-WebLogic-KeepAliveSecs----" + request.getHeader("X-WebLogic-KeepAliveSecs"));
//		log.error("Proxy?X-WebLogic-Force-JVMID----" + request.getHeader("X-WebLogic-Force-JVMID"));
//		log.error("Proxy?WL-Proxy-SSL----" + request.getHeader("WL-Proxy-SSL"));
		
		HttpSession session = request.getSession();
		Student student=(Student) session.getAttribute("student");
		
		String ip=IPUtils.getRealIP(request);
		student.setIp(ip);
		student.setE_id(eId);
		
		ResultModel res = studentService.bindIp(student);
		
		if(res.getStatus()==200) {
			res = studentService.displayQuestion(eId);
			if(res.getStatus()==200) {
				List<Question> ques = res.getListData(Question.class);
				//视图渲染
				model.addAttribute("ques", ques);
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
	
	//查看老师发布的公告
	@RequestMapping("/exam/broadcast/list")
	@ResponseBody
	public ResultModel listBroadcast(String examId) {
		//从application中获取
		List<String> attribute = (List<String>) servletContext.getAttribute("broadcast" + examId);
		return null;
	}
}
