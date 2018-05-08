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
	 * @param id
	 * @return
	 */
	ResultModel logout(String id);
	/**
	 * @return
	 */
	ResultModel examClean();
	/**
	 * @param Page
	 * @param time
	 * @return
	 */
	ResultModel	setting(String Page,long time);
}
