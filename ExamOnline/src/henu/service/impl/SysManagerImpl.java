package henu.service.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import henu.dao.TeacherDao;
import henu.entity.Teacher;
import henu.service.SysManager;
import henu.util.ResultModel;

@Service
public class SysManagerImpl implements SysManager {

	@Resource
	private TeacherDao teacherDao;
	
	@Override
	public ResultModel login(Teacher teacher) {
		try {
			 Teacher compare=teacherDao.queryById(teacher.getId());
			 if(compare.getPasswd()==teacher.getPasswd()){
				 if(compare.getIsAdmin()){
					return ResultModel.ok();
				 }else{
					 return ResultModel.build(500, "非管理员用户");
				 }
			 }else{
				 return ResultModel.build(500, "密码错误");
			 }
			
		} catch (SQLException e) {
			e.printStackTrace();
			return ResultModel.build(500, "请重新登录！");
		}
	}
	@Override
	public ResultModel logout(String id) {
		// TODO Auto-generated method stub
		return ResultModel.ok();
	}

	@Override
	public ResultModel examClean() {
		
		return ResultModel.ok();
	}

	@Override
	public ResultModel setting(String Page, long time) {
		return null;
	}

}
