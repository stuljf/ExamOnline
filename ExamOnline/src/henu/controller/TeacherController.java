package henu.controller;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import henu.entity.Exam;
import henu.entity.Question;
import henu.entity.Teacher;
import henu.service.ExamManager;
import henu.service.TeacherService;
import henu.util.ResultModel;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

	@Resource
	private TeacherService teacherService;

	@Resource
	private ExamManager examManager;

	@RequestMapping(value="/login", method=RequestMethod.POST)
	@ResponseBody
	public ResultModel login(Teacher teacher, HttpServletRequest request) {
		try {
			//登陆
			teacher.setPasswd(DigestUtils.md5DigestAsHex(teacher.getPasswd().getBytes()));
			ResultModel res = teacherService.login(teacher);

			//保存session
			HttpSession session = request.getSession();
			session.setAttribute("teacher", res.getData());

			//返回结果
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultModel.build(500, "系统错误！");
		}
	}

	@RequestMapping("/exam/list")
	@ResponseBody
	public List<Exam> getExamList(String tId, @RequestParam(defaultValue="all") String state) {
		//判空
		if (tId == null) 
			return null;

		try {
			List<Exam> exams = examManager.queryExamByTeacher(tId, state);
			return exams;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping("/exam/cancel/{eId}")
	@ResponseBody
	public ResultModel examCancel(@PathVariable String eId) {
		try {
			String[] ids = eId.split(",");
			for (String id : ids) {
				examManager.setExamState(id, "canceled");
			}
			return ResultModel.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultModel.build(500, "取消考试失败！");
		}
	}
	
	@RequestMapping("/exam/start/{eId}")
	@ResponseBody
	public ResultModel examStart(@PathVariable String eId) {
		try {
			ResultModel res = examManager.setExamState(eId, "begined");
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultModel.build(500, "开始考试失败！");
		}
	}
	
	@RequestMapping(value="/exam/add", method=RequestMethod.POST)
	@ResponseBody
	public ResultModel examAdd(Exam exam) {
		try {
			ResultModel res = examManager.createExam(exam);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultModel.build(500, "创建考试失败！");
		}
	}
	
	@RequestMapping(value="/exam/update", method=RequestMethod.POST)
	@ResponseBody
	public ResultModel examUpdate(Exam exam) {
		try {
			ResultModel res = examManager.editExam(exam);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return ResultModel.build(500, "编辑考试信息失败！");
		}
	}
	
	@RequestMapping("/question/list")
	public String questionList(Integer id, Model model) {
		try {
			//获取试卷信息
			ResultModel res = examManager.getQues(id);
			List<Question> ques = res.getListData(Question.class);
			
			//视图渲染
			
			
			//返回视图
			return "importQuestion";
			
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

}
