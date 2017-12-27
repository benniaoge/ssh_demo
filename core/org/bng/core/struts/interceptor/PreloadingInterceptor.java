package org.bng.core.struts.interceptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.bng.core.service.GenericService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.conversion.impl.XWorkConverter;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.util.reflection.ReflectionContextState;

public class PreloadingInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		before(invocation);
		return invocation.invoke();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void before(ActionInvocation invocation) throws Exception {
		final Map<String,Object> parameters = ActionContext.getContext().getParameters();
		
		ActionContext invocationContext = invocation.getInvocationContext();

		try {
			invocationContext.put(ReflectionContextState.CREATE_NULL_OBJECTS, Boolean.TRUE);
			invocationContext.put(ReflectionContextState.DENY_METHOD_EXECUTION, Boolean.TRUE);
			invocationContext.put(XWorkConverter.REPORT_CONVERSION_ERRORS, Boolean.TRUE);

			if (parameters != null) {
				
				ValueStack stack = ActionContext.getContext().getValueStack();

				List<String> preloadingTargets = new ArrayList<String>();

				// first iterate the parameters and find the special one
				for (Iterator<Map.Entry<String,Object>> iterator = parameters.entrySet().iterator(); iterator.hasNext();) {
					Map.Entry<String,Object> entry = iterator.next();
					String name = entry.getKey();
					if (name.startsWith("$")) {
						String target = name.substring(1);
						Object value = entry.getValue();
						stack.setValue(target + ".id", value);
						if (stack.findValue(target + ".id") != null) {
							preloadingTargets.add(target);
						}
					}
				}

				// find original object
				if (!preloadingTargets.isEmpty()) {
					GenericService genericService = getGenericService(ServletActionContext.getServletContext());
					for (int i = 0; i < preloadingTargets.size(); i++) {
						String target = preloadingTargets.get(i);
						try {
							Object value = genericService.get(stack.findValue(target).getClass(), (Serializable) stack.findValue(target + ".id"));
							// set the complete object again
							stack.setValue(target, value);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		} finally {
			invocationContext.put(ReflectionContextState.CREATE_NULL_OBJECTS, Boolean.FALSE);
			invocationContext.put(ReflectionContextState.DENY_METHOD_EXECUTION, Boolean.FALSE);
			invocationContext.put(XWorkConverter.REPORT_CONVERSION_ERRORS, Boolean.FALSE);
		}
	}
	
	@SuppressWarnings("rawtypes")
	protected GenericService getGenericService(ServletContext servletContext) {
		ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		return (GenericService) applicationContext.getBean("genericService");
	}

}
