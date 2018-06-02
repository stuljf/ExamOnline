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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import henu.entity.Exam;
import henu.entity.Question;
import henu.entity.Student;
import henu.entity.Teacher;
import henu.service.ExamManager;
import henu.service.TeacherService;
import henu.util.BootstrapPageResult;
import henu.util.PageBean;
import henu.util.RequestModel;
import henu.util.ResultModel;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

	@Resource
	private TeacherService teacherService;

	@Resource
	private ExamManager examManager;
	
	@Resource
	private ServletContext servletContext;

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
	
	@RequestMapping("/logout/{t_id}")
	public String login(@PathVariable String t_id, HttpServletRequest request) {
		try {
			//session
			HttpSession session = request.getSession();
			session.removeAttribute("teacher");

			//返回结果
			return "login";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	@RequestMapping(value="/changePwd", method=RequestMethod.POST)
	@ResponseBody
	public ResultModel changePwd(Teacher teacher, String newPasswd, HttpServletRequest request) {
		try {
			//获取session
			HttpSession session = request.getSession();

			//验证用户信息
			Teacher t = (Teacher) session.getAttribute("teacher");
			if (t.getPasswd().equals(DigestUtils.md5DigestAsHex(teacher.getPasswd().getBytes()))) {
				t.setPasswd(DigestUtils.md5DigestAsHex(newPasswd.getBytes()));
				//更新
				ResultModel res = teacherService.changePwd(t);
				if (res.getStatus() == 200) {
					//删除session
					session.removeAttribute("teacher");
				}
				return res;
			}

			//返回结果
			return ResultModel.build(400, "原密码不正确，请重试！");
		} catch (Exception e) {
			e.printStackTrace();
			return ResultModel.build(500, "系统错误！");
		}
	}
	
/*******************考试相关***********************************************/
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
				examManager.setExamState(Integer.parseInt(id), "canceled");
			}
			return ResultModel.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultModel.build(500, "取消考试失败！");
		}
	}
	
	@RequestMapping("/exam/start/{eId}")
	@ResponseBody
	public ResultModel examStart(@PathVariable Integer eId) {
		try {
			long timeLimit = (Long.parseLong((String) servletContext.getAttribute("timeLimit"))) * 60 * 1000;
			ResultModel res = examManager.startExam(eId, "begined", timeLimit);
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
	
	@RequestMapping("/exam/question/list")
	public String questionList(Integer examId, Model model) {
		try {
			//获取试卷信息
			List<Question> ques = examManager.getQues(examId);
			if (ques != null) {
				//视图渲染
				model.addAttribute("ques", ques);
			}
			
			model.addAttribute("examId", examId);
			//返回视图
			return "importQuestion";
			
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	@RequestMapping(value="/exam/question/import", method=RequestMethod.POST)
	@ResponseBody
	public ResultModel testpojolist(RequestModel bean) {
		if (bean == null || bean.getQuestions() == null) {
			return ResultModel.build(400, "试题不能为空~！");
		}
		
		try {
			examManager.importQues(bean.getQuestions());
		} catch (SQLException e) {
			e.printStackTrace();
			return ResultModel.build(500, "导入试题失败，请联系管理员！");
		}
	
		return ResultModel.ok();
	}
	
	/**************以下是学生管理******************/
	@RequestMapping("/exam/created/student/show")
	public String studentList(Integer examId, Model model) {
		if (examId == null) {
			return "error";
		}
		//jsp注入考试id
		model.addAttribute("examId", examId);
		
		return "importStudent";
	}
	
	@RequestMapping("/exam/created/student/list")
	@ResponseBody
	public BootstrapPageResult<Student> studentList(Integer examId,
			@RequestParam(defaultValue="1") Integer offset, 
			@RequestParam(defaultValue="10") Integer limit) {
		if (examId == null) {
			return null;
		}
		
		//创建分页对象
		PageBean<Student> bean = new PageBean<>(offset%limit == 0? offset/limit : offset/limit + 1, limit);
		//query
		examManager.queryStudent(examId, bean);
		
		BootstrapPageResult<Student> exams = new BootstrapPageResult<>(bean.getTotalCount(), bean.getPageData());
		
		return exams;
	}
	
	@RequestMapping(value="/exam/created/student/import", method=RequestMethod.POST)
	@ResponseBody
	public ResultModel studentImport(Integer examId, Student student) {
		if (examId == null) {
			return ResultModel.build(500, "缺少考试Id，倒入失败！");
		}
		//导入学生信息
		ResultModel res = examManager.addStudent(examId, student);
		return res;
	}

	
	@RequestMapping(value="/exam/created/student/importAll", method=RequestMethod.POST)
	@ResponseBody
	public ResultModel studentListImport(Integer examId, @RequestParam("file") MultipartFile multipartFile) {
		if (examId == null) {
			return ResultModel.build(500, "缺少考试Id，倒入失败！");
		}
		//导入学生信息
		ResultModel res = examManager.importStudents(examId, multipartFile);
		return res;
	}

	@RequestMapping(value="/exam/created/student/update", method=RequestMethod.POST)
	@ResponseBody
	public ResultModel studentUpdate(Integer examId, Student student) {
		if (examId == null) {
			return ResultModel.build(500, "缺少考试Id，倒入失败！");
		}
		//导入学生信息
		ResultModel res = examManager.updateStudent(examId, student);
		return res;
	}
	
	@RequestMapping("/exam/created/student/remove")
	@ResponseBody
	public ResultModel studentRemove(Integer examId, String ids) {
		if (examId == null) {
			return ResultModel.build(500, "缺少考试Id，倒入失败！");
		}
		try {
			//删除学生信息
			for (String sid : ids.split(", *")) {
				examManager.removeStudent(examId, sid);
			}
			return ResultModel.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return ResultModel.build(500, "删除失败！");
		}
	}
}
