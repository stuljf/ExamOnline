package henu.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import henu.dao.StudentDao;
import henu.entity.Student;
import henu.service.StudentService;
import henu.util.ResultModel;

@Service //业务逻辑层注解
public class StudentServiceImpl implements StudentService {

	@Resource
	private StudentDao studentDao;
	
	@Override
	public ResultModel login(Student student) {
		try {
			Student exists = studentDao.studentExists(student);
			if (exists != null)
				return ResultModel.ok(exists);
			else
				return ResultModel.build(400, "请核对帐号密码！");
		} catch (SQLException e) {
			e.printStackTrace();
			return ResultModel.build(500, "系统错误！");
		}
	}

	@Override
	public ResultModel queryExams(String id) {
		
		return null;
	}

	@Override
	public ResultModel displayQuestion(String eId) {
		
		return null;
	}

	@Override
	public ResultModel saveAsnwers(Student stu, List<String> answers) {
		
		return null;
	}

}
