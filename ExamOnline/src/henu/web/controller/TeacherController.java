package henu.web.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import henu.dao.JedisClient;
import henu.entity.Exam;
import henu.entity.Question;
import henu.entity.Student;
import henu.entity.Teacher;
import henu.service.ExamManager;
import henu.service.TeacherService;
import henu.util.BootstrapPageResult;
import henu.util.ExceptionUtil;
import henu.util.PageBean;
import henu.util.RequestModel;
import henu.util.ResultModel;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

	private Logger log = LoggerFactory.getLogger(TeacherController.class);

	@Autowired
	private TeacherService teacherService;

	@Autowired
	private ExamManager examManager;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private JedisClient jedisClient;

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
			log.error(ExceptionUtil.getStackTrace(e));
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
				if ("created".equals(examManager.getExamState(Integer.parseInt(id)))) {
					examManager.setExamState(Integer.parseInt(id), "canceled");
				}
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

			if (!"created".equals(examManager.getExamState(examId))) {
				return "examListCreated";
			}

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

		if (!"created".equals(examManager.getExamState(examId))) {
			return "examListCreated";
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

	//==========================以下是考试详情相关===============================================

	@RequestMapping("/exam/begined/details")
	public String beginedStudentList(Integer examId, Model model) {
		if (examId == null) {
			return "error";
		}
		//总人数
		long total = examManager.getStudentCount(examId, "total");
		//未登录人数
		long absent = examManager.getStudentCount(examId, "absent");
		//登陆人数
		long online = total - absent;

		//获取发布历史
		String publish = (String) servletContext.getAttribute("publish:" + examId);
		List<String> publishs;
		if (publish == null) {
			publishs=new ArrayList<String>();
		}else {
			publishs = Arrays.asList(publish.split("<<EOF>>"));
		}
		//视图渲染
		//jsp注入考试id
		model.addAttribute("examId", examId);
		model.addAttribute("total", total);
		model.addAttribute("absent", absent);
		model.addAttribute("online", online);
		model.addAttribute("publishs", publishs);

		return "examDetails";
	}

	@RequestMapping(value="/exam/begined/publish", method=RequestMethod.POST)
	@ResponseBody
	public ResultModel beginedStudentList(Integer examId, String item) {
		if (examId == null) {
			return ResultModel.build(500, "发布失败，请联系管理员！");
		}

		//获取发布历史
		String publish = (String) servletContext.getAttribute("publish:" + examId);
		if (publish == null)  {
			publish = item;
		} else {
			//添加新发布的公告
			publish += "<<EOF>>" + item;
		}
		//更新
		servletContext.setAttribute("publish:" + examId, publish);

		return ResultModel.ok();
	}

	//================================考试结束后=========================================	
	@RequestMapping("/exam/closed/scoer/{examId}")
	@ResponseBody
	public ResultModel exportScore(@PathVariable Integer examId) {
		if (examId == null) {
			return ResultModel.build(400, "考试ID不存在！");
		}

		String path = jedisClient.get("score:" + examId + ":path");
		path = "http://nginx.src/" + path;

		return ResultModel.ok(path);
	}
	
	@RequestMapping("/exam/closed/paper/{examId}")
	@ResponseBody
	public ResultModel exportPaper(@PathVariable Integer examId) {
		if (examId == null) {
			return ResultModel.build(400, "考试ID不存在！");
		}

		String path = jedisClient.get("paper:" + examId + ":path");
		path = "http://nginx.src/" + path;

		return ResultModel.ok(path);
	}

	//================================ip相关=========================================	

	@RequestMapping("/unbindIP")
	@ResponseBody
	public ResultModel unbindIp(Student student) {
		if(student.getId().isEmpty()||student.getName().isEmpty()) {
			return ResultModel.build(400, "请填写完整信息");
		}else {
			return examManager.unbindIP(student.getId(), student.getName());
		}
	}
}

