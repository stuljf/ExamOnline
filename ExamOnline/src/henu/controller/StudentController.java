package henu.controller;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import henu.entity.Exam;
import henu.entity.Student;
import henu.service.StudentService;
import henu.util.ResultModel;

@Controller
@RequestMapping("/student")
public class StudentController {

	@Resource
	private StudentService studentService;
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	@ResponseBody
	public ResultModel login(Student student, Model model, HttpServletRequest request) {
		try {
			//登陆
			ResultModel res = studentService.login(student);
			
			//保存session
			HttpSession session = request.getSession();
			session.setAttribute("student", res.getData());
			
			//返回结果
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultModel.build(500, "系统错误！");
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
				return (List<Exam>)res.getData();
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
