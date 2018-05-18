package henu.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import henu.entity.Teacher;

/**
 * @Describtion: (管理员登陆拦截器). <br/> 
 * @date: 2018年5月18日 下午9:28:32 <br/> 
 * @author Beats <br/> 
 * @version v1.0 <br/>
 * @since JDK 1.8
 */
public class TeacherInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		//获取用户信息
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("teacher");
		
		//验证
		if (teacher == null) {
			//如果不合法跳转登陆页面
			response.sendRedirect(request.getContextPath() + "/page/login");
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
