package henu.dao;

import java.sql.SQLException;

import henu.entity.Teacher;
import henu.util.PageBean;

/**
 * @ClassName TeacherDao
 * @description 针对Teacher的一系列操作
 * @author Yauto
 * @version v1.0
 */
public interface TeacherDao {

	/**
	 * 保存教师
	 * @param teacher 保存的教师
	 * @throws SQLException 
	 */
	public void save (Teacher teacher) throws SQLException;
	
	/**
	 * 根据教师id删除
	 * @param id 教师id
	 * @throws SQLException 
	 */
	public void remove (String id) throws SQLException;
	/**
	 * 修改教师信息
	 * @param teacher 需要修改的教师
	 * @throws SQLException 
	 */
	public void modify (Teacher teacher) throws SQLException;
	/**
	 * 根据教师id查询教师
	 * @param id 教师id
	 * @return 
	 * @throws SQLException 
	 */
	public Teacher queryById (String id) throws SQLException;
	/**
	 * 根据分页查询所有教师
	 * @param pageBean
	 * @throws SQLException 
	 */
	public void queryAll (PageBean<Teacher> pageBean) throws SQLException;
	/**
	 * 根据教师id和原密码修改密码
	 * @param id，password 教师id，教师密码
	 * @throws SQLException 
	 */
	public void changePassword (String id, String password) throws SQLException;
	
}
