package henu.web.lisener;

import henu.util.XMLUtil;
import org.dom4j.Document;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextInitLisener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}
	
	@Override
	public void contextInitialized(ServletContextEvent eve) {
		//获取sc
		ServletContext servletContext = eve.getServletContext();
		
		String path = this.getClass().getResource("/settings.xml").getPath();
		Document doc = XMLUtil.loadXML(path);
		//查询分页数
		String pageCount = "//setting[@name='pageCount']";
		servletContext.setAttribute("pageCount", XMLUtil.getByXPath(doc, pageCount));
		//查询时间限制
		String timeLimit = "//setting[@name='timeLimit']";
		servletContext.setAttribute("timeLimit", XMLUtil.getByXPath(doc, timeLimit));
		//查询examScanner interval
		String interval = "//setting[@name='interval']";
		servletContext.setAttribute("interval", XMLUtil.getByXPath(doc, interval));
		
	}
}
