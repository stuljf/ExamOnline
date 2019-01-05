package henu.auto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletContext;
import henu.entity.Exam;
import henu.entity.Student;
import henu.service.ExamManager;
import henu.util.ExcelWriter;
import henu.util.PropertyUtil;
import henu.util.SpringUtil;

public class ExamSave implements Runnable {
	
	private int examId;
	private ExamScore examScore;
	private ServletContext servletContext;
	
	public ExamSave(int id) {
		this.examId=id;
		this.examScore=(ExamScore) SpringUtil.getBean("examScore");
		this.servletContext=(ServletContext) SpringUtil.getBean("servletContext");
	}

	@Override
	public void run() {
		ExcelWriter writer = new ExcelWriter();
		String idT = PropertyUtil.getProperty("student.id");
		String nameT = PropertyUtil.getProperty("student.name");
		String clazzT = PropertyUtil.getProperty("student.clazz");
		String subjectT = PropertyUtil.getProperty("student.subject");
		String scoreT = PropertyUtil.getProperty("student.score");
		writer.write(0, 0, idT);
		writer.write(0, 1, nameT);
		writer.write(0, 2, clazzT);
		writer.write(0, 3, subjectT);
		writer.write(0, 4, scoreT);
		
		ExamManager examManager=(ExamManager) SpringUtil.getBean("examManagerImpl");
		
		try {
			List<Student> students=examManager.queryStudent(examId);
			Exam exam=examManager.getExamById(examId);
			if (students==null||exam==null) return ;
			for(int i=0; i<students.size(); i++) {
				//获取学生成绩
				Student s=students.get(i);
				String key=examId+":"+s.getId();
				String score=examScore.get(key);
				//将学生成绩写入excel表
				writer.write(i + 1, 0, s.getId());
				writer.write(i + 1, 1, s.getName());
				writer.write(i + 1, 2, s.getClazz());
				writer.write(i + 1, 3, exam.getSubject());
				writer.write(i + 1, 4, score == null ? "0" : score);
				//移除该学生成绩
				examScore.rm(key);
			}
			//保存excel表
			String path = "/"+examId+"-"+exam.getSubject()+"-成绩.xlsx";
			File file = new File(servletContext.getRealPath("exam")+path);
			FileOutputStream out = new FileOutputStream(file);
			writer.saveToFile(out);
			writer.close();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
