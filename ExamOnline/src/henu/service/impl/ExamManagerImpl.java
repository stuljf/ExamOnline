package henu.service.impl;

import java.sql.SQLException;
import java.util.List;

import henu.dao.ExamDao;
import henu.dao.impl.ExamDaoImpl;
import henu.entity.Exam;
import henu.entity.Question;
import henu.service.ExamManager;
import henu.util.ResultModel;

public class ExamManagerImpl implements ExamManager{

	private ExamDao examdao=new ExamDaoImpl();
	
	@Override
	public ResultModel createExam(Exam exam) {
		try {
			examdao.save(exam);
			return ResultModel.ok();
		} catch (SQLException e) {
			e.printStackTrace();
			return ResultModel.build(500, "创建考试失败");
		}
	}

	@Override
	public ResultModel importQues(String id, List<Question> qs) {
		try {
			for(Question q : qs) {
				examdao.importQues(id, q);
			}
			return ResultModel.ok();
		} catch (SQLException e) {
			e.printStackTrace();
			return ResultModel.build(500, "导入试题失败");
		}
	}

	@Override
	public ResultModel editExam(Exam exam) {
		try {
			examdao.modify(exam);
			return ResultModel.ok();
		} catch (SQLException e) {
			e.printStackTrace();
			return ResultModel.build(500, "修改考试失败");
		}
	}

	@Override
	public ResultModel cancelExam(Exam exam) {
		try {
			examdao.setStatus(exam.getId().toString(), exam.getState());
			return ResultModel.ok();
		} catch (SQLException e) {
			e.printStackTrace();
			return ResultModel.build(500, "取消考试失败");
		}
	}

}
