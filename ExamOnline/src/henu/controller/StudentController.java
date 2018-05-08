package henu.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import henu.entity.Student;
import henu.service.StudentService;
import henu.util.ResultModel;

@Controller
@RequestMapping("/student")
public class StudentController {

	@Resource
	private StudentService studentService;
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(Student student, Model model, HttpServletRequest request) {
		try {
			//登陆
			ResultModel res = studentService.login(student);
			
			//保存session
			HttpSession session = request.getSession();
			session.setAttribute("student", res.getData());
			
			//视图跳转
			if (res.getStatus() == 200) {
				return "teacher";
			} else {
				//用户名密码不匹配
				model.addAttribute("res", res.getMsg());
				return "login";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
}
