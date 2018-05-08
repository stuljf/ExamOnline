package henu.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import henu.entity.Student;

/**
 * @Describtion: (一句话描述作用). <br/> 
 * @date: 2018年5月8日 下午6:14:08 <br/> 
 * @author Beats <br/> 
 * @version v1.0 <br/>
 * @since JDK 1.8
 */
public class StudentInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		//获取用户信息
		HttpSession session = request.getSession();
		Student student = (Student) session.getAttribute("student");
		
		//验证
		if (student == null) {
			//如果不合法跳转登陆页面
			response.sendRedirect(request.getContextPath() + "/student/login");
			return false;
		}
		//如果合法就正常走下去
		return true;
	}
	
	@Override
	public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
	}
}
