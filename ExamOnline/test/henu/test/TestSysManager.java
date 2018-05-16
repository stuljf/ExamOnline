package henu.test;

import java.net.URL;
import java.util.List;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import henu.entity.Exam;
import henu.service.SysManager;
import henu.util.PageBean;
import henu.util.ResultModel;
import henu.util.XMLUtil;

//作为Spring Test的类
@RunWith(SpringJUnit4ClassRunner.class)
//加载配置文件
@ContextConfiguration(locations="classpath:spring.xml")
public class TestSysManager {
	
	//依赖注入
	@Resource 
	private SysManager sys;

	
	//读取全局设置信息
	@Test
	public void testSettings() {
		URL url = this.getClass().getResource("/settings.xml");
		System.out.println(url.getPath());
		//测试读取
		Document doc = XMLUtil.loadXML(url.getPath());
		String regex = "//setting[@name='pageCount']";
		String res = XMLUtil.getByXPath(doc, regex);
		System.out.println("分页数目" + res);
		String regex2 = "//setting[@name='timeLimit']";
		String res2 = XMLUtil.getByXPath(doc, regex2);
		System.out.println("时间限制" + res2);
		
	}
	
	//修改节点信息
	@Test
	public void settingsChange() {
		URL url = this.getClass().getResource("/settings.xml");
		System.out.println(url.getPath());
		//测试读取
		Document doc = XMLUtil.loadXML(url.getPath());
		String regex = "//setting[@name='pageCount']";
		boolean flag = XMLUtil.setElementText(doc, regex, "200");
		if (flag) {
			XMLUtil.storeXML(doc, url.getPath());
			System.out.println("修改成功");
		}
	}
}
