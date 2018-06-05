package henu.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import henu.dao.JedisClient;

//作为Spring Test的类
@RunWith(SpringJUnit4ClassRunner.class)
//加载配置文件
@ContextConfiguration(locations="classpath:spring.xml")
public class TestRedis {

	@Resource
	private JedisClient jedisClient;
	
	@Test
	public void testRedis() {
		
		String string = jedisClient.get("name");
		System.out.println(string);
	}
	
}
