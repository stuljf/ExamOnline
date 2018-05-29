package henu.controller;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import henu.dao.ExamDao;
import henu.entity.Exam;
import henu.entity.Teacher;
import henu.service.SysManager;
import henu.service.TeacherManager;
import henu.util.ResultModel;

@Controller //控制器注释
@RequestMapping("/admin")
public class AdminController {

	@Resource
	private SysManager sysManager;

	@Resource
	private TeacherManager teacherManager;
	
	//	@Resource
	//	private ExamManager examManager;
	@Resource
	private ExamDao examManager;

	@Resource
	private ServletContext servletContext;

	/*
	 * 登陆登出和修改密码
	 */
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	@ResponseBody
	public ResultModel login(Teacher teacher, Model model, HttpServletRequest request) {
		try {
			//登陆
			teacher.setPasswd(DigestUtils.md5DigestAsHex(teacher.getPasswd().getBytes()));
			ResultModel res = sysManager.login(teacher);

			//保存session
			HttpSession session = request.getSession();
			session.setAttribute("admin", res.getData());
			res.setData(null);
			//返回结果
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultModel.build(500, "系统错误！");
		}
	}

	@RequestMapping(value="/logout")
	public String logout(String id, HttpServletRequest request) {
		try {
			//清空该用户session
			HttpSession session = request.getSession();
			session.removeAttribute("admin");

			//返回视图
			return "login";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	/*
	 * 以下是系统配置相关
	 */
	
	@RequestMapping("/setting/list")
	public String showSettings() {
		return "sysConfig";
	}

	@RequestMapping(value="/setting/update", method=RequestMethod.POST)
	public String updateSettings(String pageCount, String timeLimit) {
		//判空
		if (pageCount == null || timeLimit == null) {
			return "sysConfig";
		} else {
			sysManager.setting(pageCount, timeLimit);
			//同步内存
			servletContext.setAttribute("pageCount", pageCount);
			servletContext.setAttribute("timeLimit", timeLimit);
			return "sysConfig";
		}
	}

	/*
	 * 以下是教师管理模块
	 */
	
	@RequestMapping("/teacher/list")
	@ResponseBody
	public List<Teacher> listTeacher() {
		try {
			//查询所有教师
			ResultModel res = teacherManager.queryAllTeacher();
			//返回数据
			return res.getListData(Teacher.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value="/teacher/add", method=RequestMethod.POST)
	@ResponseBody
	public ResultModel addTeacher(Teacher teacher) {
		try {
			if (teacher.getPasswd() == null) {
				teacher.setPasswd(teacher.getId());
			}
			if (teacher.getIsAdmin() == null) {
				teacher.setIsAdmin(false);
			}
			//加密
			teacher.setPasswd(DigestUtils.md5DigestAsHex(teacher.getPasswd().getBytes()));
			ResultModel res = teacherManager.addTeacher(teacher);
			//返回数据
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultModel.build(500, "系统故障，请联系管理员！");
		}
	}
	
	@RequestMapping("/teacher/{tId}")
	@ResponseBody
	public ResultModel queryTeacherById(@PathVariable String tId) {
		try {
			//查询所有教师
			ResultModel res = teacherManager.queryTeacher(tId);
			//返回数据
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultModel.build(500, "查询失败，请联系管理员！");
		}
	}
	@RequestMapping(value="/teacher/update", method=RequestMethod.POST)
	@ResponseBody
	public ResultModel updateTeacher(Teacher teacher) {
		try {
			if (teacher.getIsAdmin() == null) {
				teacher.setIsAdmin(false);
			}
			if (teacher.getPasswd() != null) {
				teacher.setPasswd(DigestUtils.md5DigestAsHex(teacher.getPasswd().getBytes()));
			}
			ResultModel res = teacherManager.updateTeacher(teacher);
			//返回数据
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultModel.build(500, "更新失败，请联系管理员！");
		}
	}
	
	@RequestMapping("/teacher/remove/{ids}")
	@ResponseBody
	public ResultModel removeTeacher(@PathVariable String ids) {
		try {
			String[] tids = ids.split(",");
			//删除对应教师
			for (String tid : tids) {
				teacherManager.deleteTeacher(tid);				
			}
			//返回数据
			return ResultModel.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultModel.build(500, "删除失败，请联系管理员！");
		}
	}
	
	/*
	 * 以下是考试清理相关 
	 */

	@RequestMapping("/exam/closed")
	@ResponseBody
	public List<Exam> examClosed() {
		try {
			//查询
			ResultModel res = sysManager.examClean();
			List<Exam> closedExams = res.getListData(Exam.class);
			return closedExams;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping("/exam/clean")
	@ResponseBody
	public ResultModel exanClean(String ids) {
		try {
			//把id分割出来
			String[] examIds = ids.trim().split(" +");
			for (String id : examIds) {
				examManager.remove(Integer.parseInt(id));
			}
			return ResultModel.ok();
		} catch (SQLException e) {
			e.printStackTrace();
			return ResultModel.build(500, "删除失败，请重试！");
		}
	}
}
