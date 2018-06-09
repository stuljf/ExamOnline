package henu.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import henu.dao.ExamDao;
import henu.dao.TeacherDao;
import henu.entity.Exam;
import henu.entity.Teacher;
import henu.service.SysManager;
import henu.util.ExceptionUtil;
import henu.util.ResultModel;
import henu.util.XMLUtil;

@Service
public class SysManagerImpl implements SysManager {
	private Logger log = LoggerFactory.getLogger(SysManager.class);
	@Resource
	private ExamDao examDao;
	
	@Resource
	private TeacherDao teacherDao;
	
	@Value("${CLOSE}")
	private String CLOSED;
	
	@Value("${CANCEL}")
	private String CANCEL;
	
	@Override
	public ResultModel login(Teacher teacher) {
		try {
			 Teacher compare=teacherDao.queryById(teacher.getId());
			 if(compare != null && compare.getPasswd().equals(teacher.getPasswd())){
				 if(compare.getIsAdmin()){
					return ResultModel.ok(compare);
				 }else{
					 return ResultModel.build(500, "非管理员用户");
				 }
			 }else{
				 return ResultModel.build(500, "用户名或密码错误");
			 }
			
		} catch (SQLException e) {
			e.printStackTrace();
			log.error(ExceptionUtil.getStackTrace(e));
			return ResultModel.build(500, ExceptionUtil.getStackTrace(e));
		}
	}

	@Override
	public ResultModel examClean() {
		try {
			List<Exam> exams = new ArrayList<>();
			// 删除已经结束的考试信息
			exams.addAll(examDao.getExamsByState(CLOSED));
			exams.addAll(examDao.getExamsByState(CANCEL));
			return ResultModel.ok(exams);
		} catch (SQLException e) {
			e.printStackTrace();
			return ResultModel.build(500, "查询考试状态信息失败，请修改bug！");
		}
	}

	@Override
	public ResultModel setting(String pageCount, String timeLimit) {
		String path = this.getClass().getResource("/settings.xml").getPath();
		//读取
		Document doc = XMLUtil.loadXML(path);
		//修改
		String pageCountRegex = "//setting[@name='pageCount']";
		boolean flag1 = XMLUtil.setElementText(doc, pageCountRegex, pageCount);
		String timeLimitRegex = "//setting[@name='timeLimit']";
		boolean flag2 = XMLUtil.setElementText(doc, timeLimitRegex, timeLimit);
		if (flag1 && flag2) {
			XMLUtil.storeXML(doc, path);
			return ResultModel.ok();
		}
		return ResultModel.build(500, "修改设置失败！");
	}

}
