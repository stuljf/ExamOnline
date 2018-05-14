package henu.service;

import henu.entity.Teacher;
import henu.util.ResultModel;

/**
 * @ClassName: TeacherManager
 * @Describtion: 教师相关操作的服务接口
 * @author Yauto 
 */
public interface TeacherManager {

	/**
	 * 添加教师
	 * @param teacher
	 * @return
	 */
	public ResultModel addTeacher(Teacher teacher);
	
	/**
	 * 根据教师id删除教师
	 * @param id
	 * @return
	 */
	public ResultModel deleteTeacher(String id);
	
	/**
	 * 修改教师信息
	 * @param teacher
	 * @return
	 */
	public ResultModel updateTeacher(Teacher teacher); 
	
	/**
	 * 查询教师信息
	 * @param id
	 * @return
	 */
	public ResultModel queryTeacher(String id);
	
	/**
	 * 查询所有教师
	 * @return
	 */
	public ResultModel queryAllTeacher();
	
	/**
	 * 重置教师密码
	 * @param id
	 * @param password
	 * @return
	 */
	public ResultModel resetPassword(String id, String password);

}
