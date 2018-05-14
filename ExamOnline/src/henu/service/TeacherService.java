package henu.service;

import henu.entity.Teacher;
import henu.util.ResultModel;


public interface TeacherService {

	/**
	 * login:(教师登录). <br/> 
	 * @param teacher
	 * @return
	 */
	ResultModel login(Teacher teacher);
	
}
