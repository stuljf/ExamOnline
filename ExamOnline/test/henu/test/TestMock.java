package henu.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

/**
 * 测试模版，不要修改或者删除，自己新建目录文件进行测试
 * @ClassName: TestMock <br/> 
 * @Describtion: 使用Spring集成的Mock框架测试web应用的控制器. <br/> 
 * @date: 2018年4月19日 下午1:58:22 <br/> 
 * @author Beats <br/> 
 * @version v1.0
 */
//这个必须使用junit4.9以上才有  
@RunWith(SpringJUnit4ClassRunner.class)  
//单元测试的时候真实的开启一个web服务，测试完毕就关闭
@WebAppConfiguration  
//配置事务的回滚,对数据库的增删改都会回滚,便于测试用例的循环利用  
@TransactionConfiguration(transactionManager="txManager", defaultRollback=true)  
@Transactional  
@ContextConfiguration(locations={"classpath:spring.xml","classpath:springmvc.xml"})  
//@ContextConfiguration(locations="classpath:spring*.xml")
public class TestMock {

	/**
	 * web应用的上下文
	 */
	@Resource 
	private WebApplicationContext context;
	/**
	 * Mock测试web的核心类
	 */
	private MockMvc mockMVC;

	@Before
	public void initMockMVC() {
		this.mockMVC = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void testStringResult() throws Exception{

		String res = mockMVC.perform(get("/hello")
				.param("hello", "This is my")
				.contentType(MediaType.TEXT_HTML)
				.accept(MediaType.TEXT_HTML))
				.andExpect(status().isOk())//期望值
				.andDo(print())//打印结果
				.andReturn().getResponse().getContentAsString();//结果字符串
		System.err.println(res);

	}

	@Test
	public void testJsonResult() throws Exception{

		String res = mockMVC.perform(get("/json")
				.param("json", "JAVA")
				.param("hah", "hah")
				//请求参数的类型
				.contentType(MediaType.TEXT_HTML)
				//希望服务器返回的值类型
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())//期望值
				.andDo(print())//打印结果
				.andReturn().getResponse().getContentAsString();//结果字符串
		System.err.println(res);
	}

	@Test
	public void testModelResult() throws Exception{

		String res = mockMVC.perform(get("/json")
				.param("json", "JAVA")
				.param("hah", "hah")
				//请求参数的类型
				.contentType(MediaType.TEXT_HTML)
				//希望服务器返回的值类型
				.accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())//期望值
					.andDo(print())//打印结果
					.andReturn().getResponse().getContentAsString();//结果字符串
		System.err.println(res);
	}
	
//	@Test
//	public void testOthers() throws Exception {
//		byte[] bytes = new byte[] {1, 2};  
//		mockMVC.perform(
//				fileUpload("/user/{id}/icon", 1L).file("icon", bytes)) //执行文件上传  
//					.andExpect(model().attribute("icon", bytes)) //验证属性相等性  
//					.andExpect(view().name("success")); //验证视图  
//		
////		mockMvc.perform(post("/user").param("name", "zhang")) //执行传递参数的POST请求(也可以post("/user?name=zhang"))  
////        .andExpect(handler().handlerType(UserController.class)) //验证执行的控制器类型  
////        .andExpect(handler().methodName("create")) //验证执行的控制器方法名  
////        .andExpect(model().hasNoErrors()) //验证页面没有错误  
////        .andExpect(flash().attributeExists("success")) //验证存在flash属性  
////        .andExpect(view().name("redirect:/user")); //验证视图  
//	}
}
