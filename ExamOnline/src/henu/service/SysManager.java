package henu.service;
import henu.entity.Teacher;
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
	 * @Description:(查询可清理考试). <br/> 
	 * @return
	 */
	ResultModel examClosed();
	
	/**
	 * @Description:(考试清理). <br/> 
	 * @return
	 */
	ResultModel examClean(String[] examIds);
	/**
	 * @param Page
	 * @param time
	 * @return
	 */
	ResultModel	setting(String pageCount, String timeLimit, String interval);
}
