package henu.service.impl;

import java.sql.SQLException;
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
import henu.service.ExamManager;
import henu.util.ExcelReader;
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

	@Override
	public ResultModel createExam(Exam exam) throws SQLException {
		//插入
		examDao.save(exam);
		//获取自增主键
		int id = examDao.getLastInsertID();
		//设置id
		exam.setId(id);
		return ResultModel.ok(exam);
	}

	@Override
	public ResultModel editExam(Exam exam) throws SQLException {
		//获取旧数据
		Exam t = examDao.queryExamsById(exam.getId());
		//填充数据
		exam.setState(t.getState());
		exam.setT_id(t.getT_id());
		//修改数据
		examDao.modify(exam);
		return ResultModel.ok();
	}

	@Override
	public ResultModel setExamState(int id, String status) throws SQLException {
		examDao.setState(id, status);
		return ResultModel.ok();
	}

	@Override
	public ResultModel importQues(int id, List<Question> ques) throws SQLException {
		
		return null;
	}

	@Override
	public List<Question> getQues(int id) throws SQLException {
		List<Question> ques = examDao.getQues(id);
		return ques;
	}

	@Override
	public ResultModel importStudents(int id, MultipartFile file) {
		//获取ExcelReader对象
		ExcelReader reader;
		try {
			reader = ExcelReader.readFile(file.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("插入学生时，读取文件失败！");
			return ResultModel.build(500, "系统错误");
		}
		//获取列名
		String[] cols = new String[3];
		cols[0] = reader.read(0, 0);
		cols[1] = reader.read(0, 1);
		cols[2] = reader.read(0, 2);
		//根据配置文件放置列
		Map<String, Integer> map = new HashMap<>();
		for (int i = 0; i < cols.length; i++) {
			if (cols[i].equals(STUDENT_ID))
				map.put(STUDENT_ID, i);
			else if (cols[i].equals(STUDENT_NAME))
				map.put(STUDENT_NAME, i);
			else if (cols[i].equals(STUDENT_CLAZZ))
				map.put(STUDENT_CLAZZ, i);
		}
		
		//开始读取
		for (int i = 1; i < reader.getTotalRows(); i++) {
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
	public ResultModel removeStudent(String id, String s_id) {
		
		return null;
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
	public ResultModel unbindIP(String s_id) {
		
		return null;
	}

	@Override
	public List<Exam> queryExamByTeacher(String t_id, String state) throws SQLException {
		return examDao.queryExamsByTeacher(t_id, state);
	}

}
