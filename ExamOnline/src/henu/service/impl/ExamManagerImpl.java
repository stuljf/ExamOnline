package henu.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import henu.dao.ExamDao;
import henu.entity.Exam;
import henu.entity.Question;
import henu.service.ExamManager;
import henu.util.ResultModel;

@Service //业务逻辑层注解
public class ExamManagerImpl implements ExamManager{

	@Resource
	private ExamDao examDao;
	
	@Override
	public ResultModel createExam(Exam exam) {
		try {
			examDao.save(exam);
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
				examDao.importQues(id, q);
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
			examDao.modify(exam);
			return ResultModel.ok();
		} catch (SQLException e) {
			e.printStackTrace();
			return ResultModel.build(500, "修改考试失败");
		}
	}

	@Override
	public ResultModel cancelExam(Exam exam) {
		try {
			examDao.setStatus(exam.getId().toString(), exam.getState());
			return ResultModel.ok();
		} catch (SQLException e) {
			e.printStackTrace();
			return ResultModel.build(500, "取消考试失败");
		}
	}

}
