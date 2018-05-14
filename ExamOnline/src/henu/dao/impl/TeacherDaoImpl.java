package henu.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
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
		String sql = "INSERT INTO teacher (id, name, passwd, isAdmin) VALUES(?, ?, ?, ?)";
		qr.update(sql, teacher.getId(), teacher.getName(), teacher.getPasswd(), teacher.getIsAdmin());
	}

	@Override
	public void remove(String id) throws SQLException {
		String sql = "DELETE FROM teacher WHERE id=?";
		qr.update(sql, id);
	}

	@Override
	public void modify(Teacher teacher) throws SQLException {
		String sql = "UPDATE teacher SET name=?, passwd=?, isAdmin=? WHERE id=?";
		qr.update(sql, teacher.getName(), teacher.getPasswd(), teacher.getIsAdmin(), teacher.getId());
	}

	@Override
	public Teacher queryById(String id) throws SQLException {
		String sql = "SELECT * FROM teacher WHERE id=?";
		Teacher teacher = qr.query(sql, new BeanHandler<Teacher>(Teacher.class), id);
		return teacher;
	}

	@Override
	public void queryAll(PageBean<Teacher> pageBean) throws SQLException {
		//设置总页数
		pageBean.setTotalCount((int) getCount());
		//查询分页信息
		String sql = "SELECT * FROM teacher LIMIT ?, ?;";
		List<Teacher> teacherList = qr.query(sql, new BeanListHandler<>(Teacher.class), 
				(pageBean.getCurrentPage()-1) * pageBean.getPageCount(), 
				pageBean.getPageCount());

		pageBean.setPageData(teacherList);
	}

	@Override
	public void changePassword(String id, String password) throws SQLException {
		String sql = "UPDATE teacher SET passwd=? WHERE id=?";
		qr.update(sql, password, id);
	}

	/**
	 * getCount:(获取总记录数). <br/> 
	 * @return
	 * @throws SQLException long
	 * @see
	 */
	private long getCount() throws SQLException {
		String sql = "SELECT COUNT(*) FROM teacher;";
		return qr.query(sql, new ScalarHandler<>());

	}
}
