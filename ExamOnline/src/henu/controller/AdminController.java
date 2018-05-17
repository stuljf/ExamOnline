package henu.controller;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import henu.dao.ExamDao;
import henu.entity.Exam;
import henu.entity.Teacher;
import henu.service.SysManager;
import henu.util.PageBean;
import henu.util.ResultModel;

@Controller //控制器注释
@RequestMapping("/admin")
public class AdminController {

	@Resource
	private SysManager sysManager;

	//	@Resource
	//	private ExamManager examManager;
	@Resource
	private ExamDao examManager;

	@Resource
	private ServletContext servletContext;

	@RequestMapping(value="/login", method=RequestMethod.POST)
	@ResponseBody
	public ResultModel login(Teacher teacher, Model model, HttpServletRequest request) {
		try {
			//登陆
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

	@RequestMapping("/setting/list")
	public String showSettings() {
		return "sysConfig";
	}

	@RequestMapping(value="/setting/update", method=RequestMethod.POST)
	@ResponseBody
	public ResultModel updateSettings(String pageCount, String timeLimit) {
		//判空
		if (pageCount == null || timeLimit == null) {
			return ResultModel.build(400, "参数不能为空！");
		} else {
			ResultModel res = sysManager.setting(pageCount, timeLimit);
			//同步内存
			servletContext.setAttribute("pageCount", pageCount);
			servletContext.setAttribute("timeLimit", timeLimit);
			return res;
		}
	}

	@RequestMapping("/teacher/list")
	public String showTeachers(Model model, Integer page) {


		return "teachers";
	}

	@RequestMapping("/exam/closed")
	public String showClosedExams(Model model, @RequestParam(defaultValue="1") Integer page) {
		int pageCount = (int) servletContext.getAttribute("pageCount");
		//设置查询参数
		PageBean<Exam> pageBean = new PageBean<>(pageCount);
		pageBean.setCurrentPage(page);
		//查询
		sysManager.examClean(pageBean );
		//视图渲染
		model.addAttribute("examList", pageBean.getPageData());
		return "examclean";
	}

	@RequestMapping("/exam/clean")
	@ResponseBody
	public ResultModel exanClean(List<Integer> ids) {
		try {
			for (Integer id : ids) {
				examManager.remove(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ResultModel.ok();
	}
}
