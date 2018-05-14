package henu.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import henu.entity.Teacher;
import henu.service.SysManager;
import henu.util.ResultModel;

@Controller //控制器注释
@RequestMapping("/admin")
public class AdminController {

	@Resource
	private SysManager sysManager;
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	@ResponseBody
	public ResultModel login(Teacher teacher, Model model, HttpServletRequest request) {
		try {
			//登陆
			ResultModel res = sysManager.login(teacher);
			
			//保存session
			HttpSession session = request.getSession();
			session.setAttribute("admin", res.getData());
			
			//返回结果
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultModel.build(500, "系统错误！");
		}
	}
	
}
