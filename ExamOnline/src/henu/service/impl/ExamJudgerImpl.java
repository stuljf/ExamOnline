package henu.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import henu.dao.ExamDao;
import henu.entity.Question;
import henu.service.ExamJudger;

@Service
public class ExamJudgerImpl implements ExamJudger {

	@Autowired
	private ExamDao examDao;
	
	@Override
	public int judge(int examId, List<Question> ques) throws SQLException {
		//获取标准答案
		List<Question> realQues = examDao.getQues(examId);
		
		//做对的题目数
		int count = 0;
		
		for (Question q : ques) {
			if (!q.getType().equals("program") && realQues.get(q.getNumber()).getAnswer().equals(q.getAnswer())) {
				count++;
			}
		}
		
		return count;
	}

}
