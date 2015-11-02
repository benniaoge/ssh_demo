package org.abin.core.struts.interceptor;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.abin.core.exception.BusinessException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.config.entities.ExceptionMappingConfig;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class ExceptionCatcherInterceptor extends AbstractInterceptor {

	private static final Log log = LogFactory.getLog(ExceptionCatcherInterceptor.class);

	private static final long serialVersionUID = 1L;

	private List<String> ignoreProperties = Arrays.asList(new String[] { "cause", "class", "localizedMessage", "stackTrace" });

	private static final String SYSTEM_MESSAGE = "system_message";

	private static final String SYSTEM_ERROR = "system_error";

	private static final String DATABASE_ERROR = "database_error";

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String result = null;
		try {
			result = invocation.invoke();
		} catch (BusinessException e) {
			log.error("errorMessage: " + e.getMessage());
			Map<String, Object> props = extractExceptionProperties(e);
			invocation.getStack().setValue("errorStack", props);
			return e.getTarget() != null ? e.getTarget() : SYSTEM_MESSAGE;
		} catch (DataAccessException e) {
			log.error("errorMessage:" + e.getMessage());
			return DATABASE_ERROR;
		} catch (RuntimeException e) {
			log.error(getExceptionMessage("RuntimeException", invocation), e);
			e.printStackTrace();
			return SYSTEM_ERROR;
		} catch (Exception e) {
			log.error(getExceptionMessage("Exception", invocation), e);
			e.printStackTrace();

			List<ExceptionMappingConfig> exceptionMappings = invocation.getProxy().getConfig().getExceptionMappings();
			String mappedResult = this.findResultFromExceptions(exceptionMappings, e);
			if (mappedResult != null) {
				result = mappedResult;
			} else {
				return SYSTEM_ERROR;
			}
		}
		return result;
	}

	private Map<String, Object> extractExceptionProperties(BusinessException businessException) {
		Map<String, Object> properties = new HashMap<String, Object>();
		// extract all the property in the BusinessException
		PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(businessException);
		for (int i = 0; i < descriptors.length; i++) {
			PropertyDescriptor descriptor = descriptors[i];
			// ignore system properties and those do not have read method
			if (descriptor.getReadMethod() == null || ignoreProperties.contains(descriptor.getName())) {
				continue;
			}
			try {
				properties.put(descriptor.getName(), PropertyUtils.getProperty(businessException, descriptor.getName()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return properties;
	}

	private String getExceptionMessage(String exception, ActionInvocation invocation) {
		return "Caught " + exception + " while invoking action: " + invocation.getAction();
	}

	private String findResultFromExceptions(List<ExceptionMappingConfig> exceptionMappings, Throwable t) {
		String result = null;

		if (exceptionMappings != null) {
			int deepest = Integer.MAX_VALUE;
			for (Iterator<ExceptionMappingConfig> iter = exceptionMappings.iterator(); iter.hasNext();) {
				ExceptionMappingConfig exceptionMappingConfig = iter.next();
				int depth = getDepth(exceptionMappingConfig.getExceptionClassName(), t);
				if (depth >= 0 && depth < deepest) {
					deepest = depth;
					result = exceptionMappingConfig.getResult();
				}
			}
		}
		return result;
	}

	public int getDepth(String exceptionMapping, Throwable t) {
		return getDepth(exceptionMapping, t.getClass(), 0);
	}

	private int getDepth(String exceptionMapping, Class<?> exceptionClass, int depth) {

		if (exceptionClass.getName().indexOf(exceptionMapping) != -1) {
			return depth;
		}

		if (exceptionClass.equals(Throwable.class)) {
			return -1;
		}

		return getDepth(exceptionMapping, exceptionClass.getSuperclass(), depth + 1);
	}

}
