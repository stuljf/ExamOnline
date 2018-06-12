package henu.service.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import henu.dao.ExamDao;
import henu.dao.StudentDao;
import henu.entity.Exam;
import henu.entity.Question;
import henu.entity.Student;
import henu.service.ExamAutoer;
import henu.service.ExamManager;
import henu.util.ExcelReader;
import henu.util.ExceptionUtil;
import henu.util.PageBean;
import henu.util.ResultModel;

@Service //业务逻辑层注解
public class ExamManagerImpl implements ExamManager {

	private Logger log = LoggerFactory.getLogger(ExamManagerImpl.class);

	@Value("${student.id}")
	private String STUDENT_ID;
	@Value("${student.name}")
	private String STUDENT_NAME;
	@Value("${student.clazz}")
	private String STUDENT_CLAZZ;

	@Resource
	private ExamDao examDao;

	@Resource
	private StudentDao studentDao;

	@Resource
	private ExamAutoer examAutoer;

	@Override
	public String getExamState(int examId) {
		try {
			return examDao.queryExamsById(examId).getState();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ResultModel createExam(Exam exam) throws SQLException {
		//插入
		int update = examDao.save(exam);
//		int id = examDao.getLastInsertID();
		//获取自增主键
		if (update > 0) {
			int id = examDao.getLastInsertID();   //可能出现获取到自增主键为0的现象
			//设置id
			exam.setId(id);

			examAutoer.queueBegin(id, exam.getStarttime().getTime());
			examAutoer.queueClose(id, exam.getEndtime().getTime());
		}

		return ResultModel.ok(exam);
	}

	@Override
	public ResultModel editExam(Exam exam) throws SQLException {
		//获取旧数据
		Exam t = examDao.queryExamsById(exam.getId());
		if ("created".equals(exam.getState())) {
			//填充数据
			exam.setState(t.getState());
			exam.setT_id(t.getT_id());
			//修改数据
			examDao.modify(exam);

			examAutoer.queueBegin(exam.getId(), exam.getStarttime().getTime());
			examAutoer.queueClose(exam.getId(), exam.getEndtime().getTime());

			return ResultModel.ok();
		} else {
			return ResultModel.build(302, "考试已经开始，请刷新界面！");
		}
	}

	@Override
	public ResultModel setExamState(int id, String status) throws SQLException {
		//取消考试时候出队。
		if (status.equals("canceled")) {
			examAutoer.dequeueBegin(id);
			examAutoer.dequeueClose(id);
		}

		if (status.equals("begined")) {
			examAutoer.dequeueBegin(id);
		}

		examDao.setState(id, status);
		return ResultModel.ok();
	}

	@Override
	public ResultModel importQues(List<Question> ques) throws SQLException {
		//获取考试id
		int examId = ques.get(0).getE_id();

		//获取考试状态，如果没开始就可以修改
		String state = examDao.queryExamsById(examId).getState();
		if ("created".equals(state)) {
			//先清空试题
			examDao.clearQues(examId);
			//再插入新试题
			for (Question question : ques) {
				examDao.importQues(question);
			}
			return ResultModel.ok();
		} else {
			return ResultModel.build(302, "考试已经开始，请刷新界面！");
		}
	}

	@Override
	public List<Question> getQues(int id) throws SQLException {
		List<Question> ques = examDao.getQues(id);
		return ques;
	}

	@Override
	public ResultModel importStudents(int id, MultipartFile file) {
		//获取考试状态，如果没开始就可以修改
		String state = null;
		try {
			state = examDao.queryExamsById(id).getState();
		} catch (SQLException e1) {
			e1.printStackTrace();
			return ResultModel.build(400, "考试不存在！");
		}
		if ("created".equals(state)) {
			//获取ExcelReader对象
			ExcelReader reader;
			try {
				reader = ExcelReader.readFile(file.getInputStream());
			} catch (Exception e) {
				e.printStackTrace();
				log.error("插入学生时，读取文件失败！");
				return ResultModel.build(500, "系统错误");
			}

			//获取行列总数
			int totalRows = reader.getTotalRows();
			//总列数
			int totalCols = reader.getTotalColumns();

			//获取列名
			String[] colNames = new String[totalCols];
			for (int i = 0; i < colNames.length; i++) {
				colNames[i] = reader.read(0, i);
			}

			//根据配置文件放置列
			Map<String, Integer> map = new HashMap<>();
			for (int i = 0; i < colNames.length; i++) {
				if (colNames[i].equals(STUDENT_ID))
					map.put(STUDENT_ID, i);
				else if (colNames[i].equals(STUDENT_NAME))
					map.put(STUDENT_NAME, i);
				else if (colNames[i].equals(STUDENT_CLAZZ))
					map.put(STUDENT_CLAZZ, i);
			}

			//开始读取
			for (int i = 1; i < totalRows; i++) {
				//如果id字段为空，舍弃 
				if (reader.read(i, map.get(STUDENT_ID)).isEmpty())
					break;
				Student s = new Student();
				s.setId(reader.read(i, map.get(STUDENT_ID)));
				s.setName(reader.read(i, map.get(STUDENT_NAME)));
				s.setClazz(reader.read(i, map.get(STUDENT_CLAZZ)));
				s.setE_id(id);
				//插入数据库
				try {
					studentDao.save(s);
				} catch (SQLException e) {
					e.printStackTrace();
					log.error("插入学生失败，可能原因：学生信息已存在！");
				}
			}		
			return ResultModel.ok();
		} else {
			return ResultModel.build(302, "考试已经开始，请刷新界面！");
		}

	}

	@Override
	public ResultModel addStudent(int id, Student stu) {
		stu.setE_id(id);
		try {
			studentDao.save(stu);
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("导入学生失败！");
			return ResultModel.build(500, "添加学生失败！");
		}
		return ResultModel.ok(stu);
	}

	@Override
	public ResultModel removeStudent(int id, String s_id) {
		try {
			studentDao.remove(s_id, id);
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("删除学生信息失败！");
			return ResultModel.build(500, "删除失败！");
		}
		return ResultModel.ok();
	}

	@Override
	public ResultModel updateStudent(int id, Student stu) {
		stu.setE_id(id);
		try {
			studentDao.modify(stu);
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("更新信息失败！");
			return ResultModel.build(500, "更新信息失败！");
		}
		return ResultModel.ok(stu);
	}

	@Override
	public void queryStudent(int id, PageBean<Student> bean) {
		try {
			studentDao.queryAll(id, bean);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ResultModel queryStudentByKey(String id, String key) {

		return null;
	}

	@Override
	public ResultModel unbindIP(String s_id, String s_name) {
		try {
			boolean flag = studentDao.unbindIp(s_id, s_name);
			if (flag)
				return ResultModel.ok();
			else
				return ResultModel.build(400, "学号姓名不匹配！");
		} catch (SQLException e) {
			e.printStackTrace();
			return ResultModel.build(500, "数据库错误！");
		}
	}

	@Override
	public List<Exam> queryExamByTeacher(String t_id, String state) throws SQLException {
		return examDao.queryExamsByTeacher(t_id, state);
	}

	@Override
	public ResultModel startExam(Integer eId, String startStatus, long timeLimit) {
		try {
			//获取考试信息
			Exam exam = examDao.queryExamsById(eId);
			//获取开始时间
			Date starttime = exam.getStarttime();
			//判断是否可以开启
			if (starttime.getTime() - new Date().getTime() < timeLimit) {
				return setExamState(eId, startStatus);
			} else {
				return ResultModel.build(500, "考试开始前" + timeLimit/60000 + "分才能开启考试！");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return ResultModel.build(500, "系统错误，请联系管理员！");
		}
	}

	@Override
	public long getStudentCount(int examId, String type) {
		try {
			return examDao.getStudentCount(examId, type);
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(ExceptionUtil.getStackTrace(e));
			return 0;
		}
	}

	@Override
	public List<Student> queryStudent(int examId) {
		try {
			List<Student> all = studentDao.queryAll(examId);
			return all;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
