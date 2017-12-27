package org.bng.core.util;

import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class WebContextUtils {
	
	public static <T> T getBean(ServletContext context, String beanName, Class<T> clazz) {
		WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(context);
		return wac.getBean(beanName, clazz);
	}

}
