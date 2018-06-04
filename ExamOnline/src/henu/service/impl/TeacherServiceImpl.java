package henu.service.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import henu.dao.TeacherDao;
import henu.entity.Teacher;
import henu.service.TeacherService;
import henu.util.ResultModel;

@Service //业务逻辑层注解
public class TeacherServiceImpl implements TeacherService {

	@Resource
	private TeacherDao teacherDao;
	
	@Override
	public ResultModel login(Teacher teacher) {
		try {
			Teacher exists = teacherDao.queryById(teacher.getId());
			if (exists != null && exists.getPasswd().equals(teacher.getPasswd()))
				return ResultModel.ok(exists);
			else
				return ResultModel.build(400, "请核对帐号密码！");
		} catch (SQLException e) {
			e.printStackTrace();
			return ResultModel.build(500, "系统错误");
		}
	}

	@Override
	public ResultModel changePwd(Teacher teacher) {
		try {
			teacherDao.modify(teacher);
		} catch (SQLException e) {
			e.printStackTrace();
			return ResultModel.build(500, "修改失败！");
		}
		return ResultModel.ok();
	}
}
