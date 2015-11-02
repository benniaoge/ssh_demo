package org.abin.core.loader;

import javax.servlet.ServletContext;

public interface ServletContextLoader {
	
	public void initServletContext(ServletContext servletContext);

	public void closeServletContext(ServletContext servletContext);

}
