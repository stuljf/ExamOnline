package henu.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringUtil implements ApplicationContextAware {

	private static ApplicationContext ac;

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		ac = arg0;
	}
	
	public static ApplicationContext getApplicationContext() {
		return ac;
	}
	
	/**
     * 获取对象
     * @param name
     * @return Object 一个以所给名字注册的bean的实例
     * @throws BeansException
     */
	public static Object getBean(String beanName) {
		Object obj = null;
		obj = ac.getBean(beanName);
		return obj;
	}
	
	/**
     * 如果bean不能被类型转换，相应的异常将会被抛出（BeanNotOfRequiredTypeException）
     * @param name       bean注册名
     * @param requiredType 返回对象类型
     * @return Object 返回requiredType类型对象
     * @throws BeansException
     */
	public static <T> T getBean(String beanName, Class<T> type) {
		T obj = null;
		obj = ac.getBean(beanName, type);
		return obj;
	}
	
	/**
     * 如果BeanFactory包含所给名称匹配的bean返回true
     * @param name
     * @return boolean
     */
    public static boolean containsBean(String name) {
        return ac.containsBean(name);
    }
    
    /**
     * 判断注册的bean是singleton还是prototype。
     * 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
     * @param name
     * @return boolean
     * @throws NoSuchBeanDefinitionException
     */
    public static boolean isSingleton(String name) {
        return ac.isSingleton(name);
    }
    
    /**
     * @param name
     * @return Class 注册对象的类型
     * @throws NoSuchBeanDefinitionException
     */
    public static Class<?> getType(String name) {
        return ac.getType(name);
    }
}
