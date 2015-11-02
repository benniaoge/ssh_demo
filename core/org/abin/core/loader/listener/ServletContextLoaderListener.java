package org.abin.core.loader.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.abin.core.loader.ServletContextLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class ServletContextLoaderListener implements ServletContextListener {
	
	private ServletContextLoader servletContextLoader;

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		this.servletContextLoader.closeServletContext(event.getServletContext());
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		this.servletContextLoader = createServletContextLoader(event);
		this.servletContextLoader.initServletContext(event.getServletContext());
	}
	
	private ServletContextLoader createServletContextLoader(ServletContextEvent event) {
		ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		return (ServletContextLoader) applicationContext.getBean("servletContextLoader");
	}

}
