package henu.service.impl;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import henu.dao.ExamDao;
import henu.dao.TeacherDao;
import henu.entity.Exam;
import henu.entity.Teacher;
import henu.service.SysManager;
import henu.util.PageBean;
import henu.util.ResultModel;
import henu.util.XMLUtil;

@Service
public class SysManagerImpl implements SysManager {

	@Resource
	private ExamDao examDao;
	
	@Resource
	private TeacherDao teacherDao;
	
	@Value("${CLOSE}")
	private String CLOSED;
	
	@Override
	public ResultModel login(Teacher teacher) {
		try {
			 Teacher compare=teacherDao.queryById(teacher.getId());
			 if(compare.getPasswd().equals(teacher.getPasswd())){
				 if(compare.getIsAdmin()){
					return ResultModel.ok(compare);
				 }else{
					 return ResultModel.build(500, "非管理员用户");
				 }
			 }else{
				 return ResultModel.build(500, "密码错误");
			 }
			
		} catch (SQLException e) {
			e.printStackTrace();
			return ResultModel.build(500, "请重新登录！");
		}
	}

	@Override
	public ResultModel examClean(PageBean<Exam> bean) {
		try {
			// 删除已经结束的考试信息
			examDao.getExamsByState(bean, CLOSED);
			return ResultModel.ok(bean);
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
