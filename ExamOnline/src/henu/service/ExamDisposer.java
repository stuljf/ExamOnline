package henu.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;

import henu.dao.ExamDao;
import henu.dao.JedisClient;
import henu.entity.Exam;
import henu.entity.Student;
import henu.util.ExcelWriter;
import henu.util.ExceptionUtil;
import henu.util.FtpUtil;
import henu.util.PropertyUtil;
import henu.util.SpringUtil;

public class ExamDisposer implements Runnable {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	private ServletContext servletContext;
	private int examId;
	private ExamManager examManager;
	private ExamDao examDao;
	private JedisClient jedisClient;
	
	public ExamDisposer(int examId) {
		this.examId = examId;
		this.examManager = (ExamManager) SpringUtil.getBean("examManagerImpl");
		this.examDao = (ExamDao) SpringUtil.getBean("examDaoImpl");
		this.servletContext = ContextLoader.getCurrentWebApplicationContext().getServletContext();
		this.jedisClient = (JedisClient) SpringUtil.getBean("jedisClientSingle");
	}
	
//	@Override
	public void run() {
		
		log.error("进入线程...");
		
		//结束考试后把成绩写入Excel并保存到ftp
		ExcelWriter writer = new ExcelWriter();
		//写入第一行
		writer.write(0, 0, PropertyUtil.getProperty("student.id"));
		writer.write(0, 1, PropertyUtil.getProperty("student.name"));
		writer.write(0, 2, PropertyUtil.getProperty("student.clazz"));
		writer.write(0, 3, PropertyUtil.getProperty("student.subject"));
		writer.write(0, 4, PropertyUtil.getProperty("student.score"));
		//写入数据
		//获取学生信息
		List<Student> list = examManager.queryStudent(examId);
		try {
			//获取考试信息
			Exam exam = examDao.queryExamsById(examId);
			//如果没有考生就直接结束
			if (list == null) return;
			for (int i = 0, len = list.size(); i < len; i++) {
				//学生信息
				Student s = list.get(i);
				//成绩
				int score = (int) servletContext.getAttribute("score:" + examId + ":" + s.getId());
				writer.write(i + 1, 0, s.getId());
				writer.write(i + 1, 1, s.getName());
				writer.write(i + 1, 2, s.getClazz());
				writer.write(i + 1, 3, exam.getSubject());
				writer.write(i + 1, 4, score+"");
				
				servletContext.removeAttribute("score:" + examId + ":" + s.getId());
			}
			
			//保存excel到流中
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			writer.saveToFile(out);
			
			//关闭ExcelWriter
			writer.close();
			
			//转换成输入流
			ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
			
			//设置保存路径
			String filePath = "exams/" + examId + "-" + exam.getSubject();
			String fileName = exam.getSubject() + "-考试成绩.xlsx";
			
			boolean flag = FtpUtil.uploadFile(PropertyUtil.getProperty("ftp.url"), 
					Integer.parseInt(PropertyUtil.getProperty("ftp.port")),
					PropertyUtil.getProperty("ftp.username"),
					PropertyUtil.getProperty("ftp.passwd"),
					filePath, fileName, in);
			if (!flag) {
				log.error("上传考试答案失败，考试信息：" + "科目_" + exam.getSubject() + ", 时间_" + exam.getStarttime());
			} else {
				log.error("保存数据成功...");
				jedisClient.set("score:" + examId + ":path", filePath + "/" + fileName);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			log.error(ExceptionUtil.getStackTrace(e));
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(ExceptionUtil.getStackTrace(e));
		} catch (IOException e) {
			e.printStackTrace();
			log.error(ExceptionUtil.getStackTrace(e));
		}
			
	}

}
