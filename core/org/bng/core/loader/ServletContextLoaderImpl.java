package org.bng.core.loader;

import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ServletContextLoaderImpl implements ServletContextLoader {
	
	private static final Log logger = LogFactory.getLog(ServletContextLoaderImpl.class);
	
	private List<ServletContextLoader> servletContextLoaders;

	private String loaders;

	@Override
	public void initServletContext(ServletContext servletContext) {
		if (logger.isDebugEnabled()) {
			logger.debug("Init Servlet Context:" + loaders);
		}
		for (ServletContextLoader servletContextLoader : servletContextLoaders) {
			servletContextLoader.initServletContext(servletContext);
		}
	}

	@Override
	public void closeServletContext(ServletContext servletContext) {
		for (ServletContextLoader servletContextLoader : servletContextLoaders) {
			servletContextLoader.closeServletContext(servletContext);
		}
	}
	
	public void setServletContextLoaders(List<ServletContextLoader> servletContextLoaders) {
		this.servletContextLoaders = servletContextLoaders;
		this.loaders = servletContextLoaders.toString();
	}

}
