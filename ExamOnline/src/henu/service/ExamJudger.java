package henu.service;

import java.sql.SQLException;
import java.util.List;

import henu.entity.Question;

/**
 * @Describtion: (试卷自动批改). <br/> 
 * @date: 2018年5月19日 下午6:16:03 <br/> 
 * @author Beats <br/> 
 * @version v1.0 <br/>
 * @since JDK 1.8
 */
public interface ExamJudger {
	
	int judge(int examId, List<Question> ques) throws SQLException;
	
}
