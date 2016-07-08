package org.abin.core.loader;

import java.util.Map;

import javax.servlet.ServletContext;

import org.abin.core.search.SearchMapping;
import org.hibernate.SessionFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SystemLoader implements ServletContextLoader {
	
	private static final String DEFAULT_SESSION_FACTORY_BEAN_NAME = "sessionFactory";

	private String sessionFactoryBeanName = DEFAULT_SESSION_FACTORY_BEAN_NAME;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void initServletContext(ServletContext servletContext) {
		SessionFactory sessionFactory = (SessionFactory) WebApplicationContextUtils.getWebApplicationContext(servletContext).getBean(sessionFactoryBeanName);
		Map persistentClassMetadata = sessionFactory.getAllClassMetadata();
		SearchMapping.init(persistentClassMetadata.keySet());
	}

	@Override
	public void closeServletContext(ServletContext servletContext) {
	}
	
	public String getSessionFactoryBeanName() {
		return sessionFactoryBeanName;
	}
	
	public void setSessionFactoryBeanName(String sessionFactoryBeanName) {
		this.sessionFactoryBeanName = sessionFactoryBeanName;
	}

}
