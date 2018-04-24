package henu.service;

import henu.entity.Student;
import henu.util.ResultModel;

/**
 * @ClassName: StudentService <br/> 
 * @Describtion: 学生相关操作的服务接口. <br/> 
 * @date: 2018年4月17日 下午5:20:04 <br/> 
 * @author Beats <br/> 
 * @version v1.0
 */
public interface StudentService {
	
	/**
	 * login:(学生登录). <br/> 
	 * @param student
	 * @return ResultModel	返回数据模型
	 */
	ResultModel login(Student student);
	
	/**
	 * queryExams:(根据学生id查看所对应的考试). <br/> 
	 * @param id
	 * @return ResultModel
	 */
	ResultModel queryExams(String id);
	
	/**
	 * displayQuestion:(根据考试的id来获取试卷信息). <br/> 
	 * @param eId
	 * @return ResultModel
	 */
	ResultModel displayQuestion(String eId);
	
	/**
	 * saveAsnwers:(考生答案保存). <br/> 
	 * @param eId
	 * @param answers void
	 */
	ResultModel saveAsnwers(String eId, String[] answers);
}
