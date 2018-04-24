package henu.util;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.junit.Test;

import henu.entity.Exam;

/**
 * @ClassName: JsonUtil <br/> 
 * @Describtion: JSON与Java Bean之间的转换. <br/> 
 * @date: 2018年4月17日 下午10:29:39 <br/> 
 * @author Beats <br/> 
 * @version v1.0
 */
public class JsonUtil {

	 // 定义ObjectMapper对象，用于数据转换
    private static final ObjectMapper mapper = new ObjectMapper();
	
    /**
     * getJSON:(对象转换成JSON). <br/> 
     * @param bean
     * @return String
     */
    public static String getJson(Object object) {
    	try {
    		return mapper.writeValueAsString(object);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	return null;
    }

    /**
     * getBean:(JSON字符串转对象). <br/> 
     * @param json
     * @param t
     * @return T
     */
	public static <T> T getBean(String json, Class<T> t) {
		try {
			return mapper.readValue(json, t);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
     * jsonToObject:(JSON字符串转对象). <br/> 
     * @param json
     * @param t
     * @return T
     */
	public static <T> List<T> getBeanList(String json, Class<T> beanType) {
		try {
			//方案一：
//			JavaType javaType = mapper.getTypeFactory().constructCollectionType(List.class, beanType);
			//方案二：
			JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, beanType);
			return mapper.readValue(json, javaType);
			//方案三
//			TypeReference<T> typeReference = new TypeReference<T>() {};
//			return mapper.readValue(json, typeReference);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Test
	public void testGetJson() {
		//简单类型
		Exam e = new Exam();
		e.setId(100);
		e.setSubject("English");
		e.setState("Created");
		e.setStarttime(new Date());
		e.setEndtime(new Date());
		e.setT_id("1234567890");
		
		String json = JsonUtil.getJson(e);
		System.out.println(json);
		
	}
	
	@Test
	public void testGetBean() {
		String json = "{\"subject\":\"English\",\"starttime\":\"1523981055836\",\"endtime\":\"1523981055836\",\"state\":\"Created\",\"teacherId\":\"1234567890\"}";
		Exam e = JsonUtil.getBean(json, Exam.class);
		System.out.println(e.toString());
	}
	
	@Test
	public void testGetBeanList() {
		String json = "[{\"subject\":\"English\",\"starttime\":\"1523981055836\",\"endtime\":\"1523981055836\",\"state\":\"Created\",\"teacherId\":\"1234567890\"},{\"subject\":\"English\",\"starttime\":\"1523981055836\",\"endtime\":\"1523981055836\",\"state\":\"Created\",\"teacherId\":\"1234567890\"}]";
		List<Exam> es = JsonUtil.getBeanList(json, Exam.class);
		System.out.println(es);
		
	}
}
