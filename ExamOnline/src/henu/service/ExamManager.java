package henu.service;

import java.util.List;

import henu.entity.Exam;
import henu.entity.Question;
import henu.util.ResultModel;

public interface ExamManager {
	
	public ResultModel createExam(Exam exam);
	
	public ResultModel importQues(String id, List<Question> q);
	
	public ResultModel editExam(Exam exam);
	
	public ResultModel cancelExam(Exam exam);
	
}
