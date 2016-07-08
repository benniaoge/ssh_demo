package org.abin.core.struts.interceptor;

import org.abin.core.exception.BusinessException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class ExceptionCatcherInterceptor extends AbstractInterceptor {

	private static final Log log = LogFactory.getLog(ExceptionCatcherInterceptor.class);

	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String result = null;
		try {
			result = invocation.invoke();
		} catch (BusinessException e) {
			log.error("errorMessage: " + e.getMessage());
			e.printStackTrace();
			
			invocation.getStack().set("errorMessage", e.getMessage());
			return "input";
		} catch (RuntimeException e) {
			log.error(getExceptionMessage("RuntimeException", invocation), e);
			e.printStackTrace();
			return "system_error";
		} catch (Exception e) {
			log.error(getExceptionMessage("Exception", invocation), e);
			e.printStackTrace();
			return "system_error";
		}
		return result;
	}

	private String getExceptionMessage(String exception, ActionInvocation invocation) {
		return "Caught " + exception + " while invoking action: " + invocation.getAction();
	}

}
