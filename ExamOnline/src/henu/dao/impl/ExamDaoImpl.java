package henu.dao.impl;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.springframework.stereotype.Repository;

import henu.dao.ExamDao;
import henu.entity.Exam;
import henu.entity.Question;

@Repository //持久化层注解
public class ExamDaoImpl implements ExamDao {
	
	@Resource  //依赖注入，不用手动new对象
	private QueryRunner qr;

	@Override
	public void save(Exam exam) throws SQLException {
		// TODO Auto-generated method stub
		String sql="INSERT INTO exam VALUES(NULL, ?, ?, ?, DEFAULT, ?);";
		qr.update(sql, exam.getSubject(), exam.getStarttime(), 
				exam.getEndtime(), exam.getT_id());
	}

	@Override
	public void remove(Integer id) throws SQLException {
		// TODO Auto-generated method stub
		String sql="DELETE FROM exam WHERE id = ?;";
		qr.update(sql, id);
	}

	@Override
	public void modify(Exam exam) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "UPDATE exam SET subject = ?, starttime = ?, "
				+ "endtime = ?, state = ? WHERE id = ?;";
		qr.update(sql, exam.getSubject(), exam.getStarttime(), 
				exam.getEndtime(), exam.getState(), exam.getId());
	}

	@Override
	public List<Exam> queryExamsByTeacher(String teacherId, String state) throws SQLException {
		//判断
		String sql = null;
		List<Exam> exams = null;
		switch (state) {
		case "created":
			sql = "SELECT * FROM exam WHERE t_id = ? AND state = ?;";
			exams = qr.query(sql, new BeanListHandler<>(Exam.class), teacherId, state);
			break;
			
		case "begined":
			sql = "SELECT * FROM exam WHERE t_id = ? AND state = ?;";
			exams = qr.query(sql, new BeanListHandler<>(Exam.class), teacherId, state);
			break;
			
		case "closed":
			sql = "SELECT * FROM exam WHERE t_id = ? AND state = ?;";
			exams = qr.query(sql, new BeanListHandler<>(Exam.class), teacherId, state);
			break;
			
		default: 
			sql = "SELECT * FROM exam WHERE t_id = ?;";
			exams = qr.query(sql, new BeanListHandler<>(Exam.class), teacherId);
			break;
		}
		
		return exams;
	}

	@Override
	public Exam queryExamsById(int examId) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM exam WHERE id = ?;";		
		return qr.query(sql, new BeanHandler<>(Exam.class), examId);
	}

	@Override
	public void setState(int id, String status) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "UPDATE exam SET state = ? WHERE id = ?;";
		qr.update(sql, status, id);
	}

	@Override
	public List<Question> getQues(int id) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM question WHERE e_id = ?;";
		return qr.query(sql, new BeanListHandler<>(Question.class), id);
	}

	@Override
	public void importQues(String id, Question ques) throws SQLException {
		// TODO Auto-generated method stub
		String sql="INSERT INTO question VALUES(NULL, ?, ?, ?, ?, ?, ?);";
		qr.update(sql, ques.getNumber(), ques.getTitle(), ques.getType(), 
				ques.getSelection(), ques.getAnswer(), ques.getE_id(), id);
	}

	@Override
	public List<Exam> getExamsByState(String status) throws SQLException {
		String sql = "SELECT * FROM exam WHERE state = ?;";
		List<Exam> exams = qr.query(sql, new BeanListHandler<>(Exam.class), status);
		return exams;
	}
	
	@SuppressWarnings("unused")
	private long getCount() throws SQLException {
		String sql = "SELECT COUNT(*) FROM exam;";
		return (long) qr.query(sql, new ScalarHandler<>());
		
	}

	@Override
	public int getLastInsertID() throws SQLException {
		String sql = "SELECT LAST_INSERT_ID();";
		BigInteger t = (BigInteger) qr.query(sql, new ScalarHandler<>());
		return t.intValue();
	}
}
