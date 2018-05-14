package henu.service.impl;

import java.sql.SQLException;

import henu.dao.TeacherDao;
import henu.entity.Teacher;
import henu.service.TeacherService;
import henu.util.ResultModel;

public class TeacherServiceImpl implements TeacherService {

	private TeacherDao teacherDao;
	
	@Override
	public ResultModel addTeacher(Teacher teacher) {
		try {
			teacherDao.save(teacher);
			return ResultModel.ok();
		} catch (SQLException e) {
			e.printStackTrace();
			return ResultModel.build(500, "系统出错");
		}
	}

	@Override
	public ResultModel deleteTeacher(String id) {
		try {
			teacherDao.remove(id);
			return ResultModel.ok();
		} catch (SQLException e) {
			e.printStackTrace();
			return ResultModel.build(500, "系统出错");
		}
	}

	@Override
	public ResultModel updateTeacher(Teacher teacher) {
		try {
			teacherDao.update(teacher);
			return ResultModel.ok();
		} catch (SQLException e) {
			e.printStackTrace();
			return ResultModel.build(500, "系统出错");
		}
	}

	@Override
	public ResultModel queryTeacher(String id) {
		try {
			teacherDao.queryById(id);
			return ResultModel.ok();
		} catch (SQLException e) {
			e.printStackTrace();
			return ResultModel.build(500, "系统出错");
		}
	}

	@Override
	public ResultModel queryAllTeacher() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ResultModel resetPassword(String id, String password) {
		try {
			teacherDao.changePassword(id, password);
			return ResultModel.ok();
		} catch (SQLException e) {
			e.printStackTrace();
			return ResultModel.build(500, "系统出错");
		}
	}

	
}
