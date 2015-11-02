package org.abin.core.struts.interceptor;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class SearchCriteriaCleanupInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();

		if (shouldCleanup(request)) {
			HttpSession session = request.getSession();
			// clear up all the search criteria in httpSession
			if (session.getAttribute("searchCriteriaList") != null) {
				Set<String> searchCriteriaList = (Set<String>) session.getAttribute("searchCriteriaList");
				for (String searchCriteriaKey : searchCriteriaList) {
					session.removeAttribute(searchCriteriaKey);
				}
			}
		}
		return invocation.invoke();
	}

	protected boolean shouldCleanup(HttpServletRequest request) {
		if (request.getParameter("isPage") == null) {
			return true;
		}
		return false;
	}

}
