package henu.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import henu.entity.Exam;

/**
 * @ClassName: MockTestController <br/> 
 * @Describtion: Mock框架测试用例. <br/> 
 * @date: 2018年4月19日 下午2:02:47 <br/> 
 * @author Beats <br/> 
 * @version v1.0
 */
@Controller
public class MockTestController {

	@RequestMapping("/hello")
	@ResponseBody
	public String hello(String hello) {
		return hello + " world!";
	}
	
	@RequestMapping("/json")
	@ResponseBody
	public Exam json(String json) {
		Exam e = new Exam();
		e.setId(123);
		e.setSubject(json);
		return e;
	}
	
}
