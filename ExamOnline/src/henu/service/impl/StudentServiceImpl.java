package henu.service.impl;

import java.sql.SQLException;

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
			return ResultModel.ok(exists);
		} catch (SQLException e) {
			e.printStackTrace();
			return ResultModel.build(500, "请重新登录！");
		}
	}

	@Override
	public ResultModel queryExams(String id) {
		//待完成
		return null;
	}

	@Override
	public ResultModel displayQuestion(String eId) {
		//待完成
		return null;
	}

	@Override
	public ResultModel saveAsnwers(String eId, String[] answers) {
		//待完成
		return null;
	}

}
