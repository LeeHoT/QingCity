package com.qingcity.test.init;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class InitSpringConfigFile {
	
	@SuppressWarnings("resource")
	public static <T> T getBean(Class<T> clazz){
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:config/applicationContext.xml");
		T t = null;
		t = context.getBean(clazz);
		return t;
	}
}
