package henu.util;

import java.util.List;

import henu.entity.Question;

/**
 * @Describtion: (接收Question列表). <br/> 
 * @date: 2018年6月1日 下午5:17:42 <br/> 
 * @author Beats <br/> 
 * @version v1.0 <br/>
 * @since JDK 1.8
 */
public class RequestModel {
	
	private List<Question> questions;
	public List<Question> getQuestions() {
		return questions;
	}
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
}
