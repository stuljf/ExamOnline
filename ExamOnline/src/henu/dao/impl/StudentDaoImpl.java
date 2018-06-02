package henu.dao.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.springframework.stereotype.Repository;

import henu.dao.StudentDao;
import henu.entity.Student;
import henu.util.PageBean;

@Repository //持久化层注解
public class StudentDaoImpl implements StudentDao {

	@Resource  //依赖注入，不用手动new对象
	private QueryRunner qr;
	
	@Override
	public void save(Student student) throws SQLException {
		String sql = "INSERT INTO student VALUES(?, ?, DEFAULT, ?, ?);";
		qr.update(sql, student.getId(), student.getName(), student.getClazz(),
				student.getE_id());
	}

	@Override
	public void remove(String id, int examId) throws SQLException {
		String sql = "DELETE FROM student WHERE id = ? AND e_id = ?;";
		qr.update(sql, id, examId);
	}

	@Override
	public List<Student> queryStudentById(String id) throws SQLException {
		String sql = "SELECT * FROM student WHERE id = ?;";
		return qr.query(sql, new BeanListHandler<>(Student.class), id);
	}

	@Override
	public void modify(Student student) throws SQLException {
		String sql = "UPDATE student SET name = ?, clazz = ? WHERE id = ?;";
		qr.update(sql, student.getName(), 
				student.getClazz(), student.getId());
	}

	@Override
	public void queryAll(int examId, PageBean<Student> stus) throws SQLException {
		//设置总页数
		stus.setTotalCount((int) getCount(examId));
		//查询分页信息
		String sql = "SELECT * FROM student WHERE e_id = ? LIMIT ?, ?;";
		List<Student> studentList = qr.query(sql, new BeanListHandler<>(Student.class), 
				examId, stus.getCurrentPage() * stus.getPageCount(), 
				stus.getPageCount());
		
		stus.setPageData(studentList);
	}

	@Override
	public List<Student> studentExists(Student student) throws SQLException {
		String sql = "SELECT * FROM student WHERE id = ? AND name = ?;";
		return qr.query(sql, new BeanListHandler<>(Student.class), student.getId(), student.getName());
	}

	@Override
	public void modifyIp(Student student) throws SQLException {
		String sql = "UPDATE student SET ip = ? WHERE id = ? AND e_id = ?;";
		qr.update(sql, student.getIp(),	student.getId(), student.getE_id());
	}

	@Override
	public Student query(Student student) throws SQLException {
		String sql = "SELECT * FROM student WHERE id = ? AND e_id = ?;";
		return qr.query(sql, new BeanHandler<>(Student.class), student.getId(), student.getE_id());
	}
	
	/**
	 * getCount:(获取总记录数). <br/> 
	 * @return
	 * @throws SQLException long
	 * @see
	 */
	private long getCount(int examId) throws SQLException {
		String sql = "SELECT COUNT(*) FROM (SELECT * FROM student WHERE e_id = ?) AS student_exam_tmp;";
		return (long) qr.query(sql, new ScalarHandler<>(), examId);
		
	}

}
