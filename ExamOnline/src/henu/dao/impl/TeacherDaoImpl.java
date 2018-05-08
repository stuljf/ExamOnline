package henu.dao.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.springframework.stereotype.Repository;

import henu.dao.TeacherDao;
import henu.entity.Teacher;
import henu.util.PageBean;

@Repository
public class TeacherDaoImpl implements TeacherDao {

	@Resource
	private QueryRunner qr;
	
	@Override
	public void save(Teacher teacher) throws SQLException {
		String sql = "insert into teacher (id, name, passwd, isAdmin) values(?, ?, ?, ?)";
		qr.update(sql, teacher.getId(), teacher.getName(), teacher.getPasswd(), teacher.getIsAdmin());
	}

	@Override
	public void remove(String id) throws SQLException {
		String sql = "delete from teacher where id=?";
		qr.update(sql, id);
	}

	@Override
	public void modify(Teacher teacher) throws SQLException {
		String sql = "update teacher set name=?, passwd=?, isAdmin=? where id=?";
		qr.update(sql, teacher.getName(), teacher.getPasswd(), teacher.getIsAdmin(), teacher.getId());
	}

	@Override
	public Teacher queryById(String id) throws SQLException {
		String sql = "select * from teacher where id=?";
		Teacher teacher = qr.query(sql, new BeanHandler<Teacher>(Teacher.class), id);
		return teacher;
	}

	@Override
	public void queryAll(PageBean<Teacher> pageBean) {
		
	}

	@Override
	public void changePassword(String id, String password) throws SQLException {
		String sql = "update teacher set passwd=? where id=?";
		qr.update(sql, password, id);
	}

}
