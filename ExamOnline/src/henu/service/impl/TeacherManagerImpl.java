package henu.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import henu.dao.TeacherDao;
import henu.entity.Teacher;
import henu.service.TeacherManager;
import henu.util.ResultModel;

@Service
public class TeacherManagerImpl implements TeacherManager {

	@Resource
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
			teacherDao.modify(teacher);
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
		try {
			List<Teacher> list = teacherDao.queryAll();
			return ResultModel.ok(list);
		} catch (SQLException e) {
			e.printStackTrace();
			return ResultModel.build(500, "查询失败！");
		}
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
