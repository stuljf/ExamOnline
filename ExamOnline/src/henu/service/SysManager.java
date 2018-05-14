package henu.service;
import henu.entity.Exam;
import henu.entity.Teacher;
import henu.util.PageBean;
import henu.util.ResultModel;
/**
 * @author Administrator
 *
 */

public interface SysManager {

	/**	
	 * 管理员登录
	 * @param teacher
	 * @return 
	 */
	public ResultModel login(Teacher teacher);

	/**
	 * @Description:(考试清理). <br/> 
	 * @return
	 */
	ResultModel examClean(PageBean<Exam> pageBean);
	/**
	 * @param Page
	 * @param time
	 * @return
	 */
	ResultModel	setting(String pageCount, String timeLimit);
}
