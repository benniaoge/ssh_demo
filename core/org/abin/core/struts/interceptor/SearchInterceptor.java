package org.abin.core.struts.interceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.abin.core.search.SearchComponent;
import org.abin.core.search.SearchContainer;
import org.abin.core.search.SearchContext;
import org.abin.core.search.SearchMapping;
import org.abin.core.struts.action.SearchActionSupport;
import org.abin.core.util.BeanUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.config.ConfigurationManager;
import com.opensymphony.xwork2.config.providers.XWorkConfigurationProvider;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.PreResultListener;
import com.opensymphony.xwork2.ognl.OgnlValueStackFactory;
import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.util.reflection.ReflectionContextState;

public class SearchInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		before(invocation);
		return invocation.invoke();
	}

	@SuppressWarnings("unchecked")
	protected void before(ActionInvocation invocation) throws Exception {

		if (invocation.getAction() instanceof SearchActionSupport) {
			SearchActionSupport action = (SearchActionSupport) invocation.getAction();
			ActionContext actionContext = invocation.getInvocationContext();

			// get all the parameters in request
			final Map<String, Object> parameters = actionContext.getParameters();

			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();

			String searchCriteriaKey = getSearchCriteriaKey(request.getRequestURI());

			// searchCriteriaKey contains in httpSession, put the searchCriteria into action
			if (session.getAttribute(searchCriteriaKey) != null) {
				Map<String, Object> searchCriteria = (Map<String, Object>) session.getAttribute(searchCriteriaKey);

				for (Iterator<Map.Entry<String, Object>> iter = searchCriteria.entrySet().iterator(); iter.hasNext();) {
					Map.Entry<String, Object> entry = iter.next();
					injectParameter(actionContext.getValueStack(), actionContext.getContextMap(), entry.getKey(), entry.getValue());
				}
			}

			// check parameters from request and reset searchComponent
			if (parameters != null) {

				SearchComponent searchComponent = action.getSearchComponent() == null ? new SearchComponent() : action.getSearchComponent();

				Map<String, Object> searchElements = new HashMap<String, Object>();

				// iterate every parameter and create searchComponent
				for (Iterator<Map.Entry<String, Object>> iter = parameters.entrySet().iterator(); iter.hasNext();) {
					Map.Entry<String, Object> entry = iter.next();
					String key = entry.getKey();
					String[] value = (String[]) entry.getValue();

					// add to searchElements when neccessary
					addToSearchElements(searchComponent, searchElements, key, value);
				}

				// create search component using searchElements
				searchComponent.addSearchElements(searchElements);

				// set back searchComponent to action
				action.setSearchComponent(searchComponent);
			}

			// register a new PreResultListener，restore the searchCriteria into httpSession
			invocation.addPreResultListener(new SearchCriteriaSessionStore(searchCriteriaKey));
		}
	}

	private String getSearchCriteriaKey(String requestURI) {
		return requestURI.substring(requestURI.indexOf("/", 1) + 1) + "_SearchCriteria";
	}

	private void injectParameter(ValueStack valueStack, Map<String, Object> contextMap, String key, Object parameter) {
		try {
			
			ReflectionContextState.setCreatingNullObjects(contextMap, true);
			ReflectionContextState.setDenyMethodExecution(contextMap, true);
			ReflectionContextState.setReportingConversionErrors(contextMap, true);

			// set parameter here using valueStack
			if (acceptableName(key)) {
				try {
					valueStack.setValue(key, parameter);
				} catch (RuntimeException e) {
					e.printStackTrace();
				}
			}

		} finally {
			ReflectionContextState.setCreatingNullObjects(contextMap, false);
			ReflectionContextState.setDenyMethodExecution(contextMap, false);
			ReflectionContextState.setReportingConversionErrors(contextMap, false);
		}
	}

	private void addToSearchElements(SearchComponent searchComponent, Map<String, Object> searchElements, String key, String[] value) {
		SearchContext searchContext = new SearchContext(key);
		// only those not null inputs and satisfied key will be included in search element
		if (searchContext.validate()) {
			// create real parameter here including type conversion
			Object parameter = null;
			if (searchContext.isOrderSentence() || searchContext.isGroupSentence()) {
				if (searchContext.isOrderSentence()) {
					searchComponent.resetOrderSentence();
				}
				parameter = value[0];
			} else {
				if (!BeanUtils.isArrayEmpty(value))
					parameter = createParameter(searchContext, value);
			}
			searchElements.put(key, parameter);
		}
	}

	protected Object createParameter(SearchContext searchContext, String[] value) {
		SearchContainer searchContainer = new SearchContainer();

		if (value.length == 1) {
			return getValue(searchContext, value[0], searchContainer);
		} else {
			List<Object> parameters = new ArrayList<Object>();
			for (int i = 0; i < value.length; i++) {
				parameters.add(getValue(searchContext, value[i], searchContainer));
			}
			return parameters;
		}
	}

	private Object getValue(SearchContext searchContext, String value, SearchContainer searchContainer) {

		String target = searchContext.getTarget(); // get search target, e.g. eq.user.name => user.name
		String context = searchContext.getContext(); // get search context, e.g. eq.user.name => user

		// create a searchContainer context
		if (!searchContainer.contains(context)) {
			searchContainer.set(context, SearchMapping.getPersistentClass(context));
		}

		// add % between value when operation is like
		if (searchContext.getOperation().toUpperCase().equals("LIKE")) {
			value = "%" + value + "%";
		}

		// using ognl support to determine the input parameter
		OgnlValueStackFactory ognlValueStackFactory = new OgnlValueStackFactory();
		ConfigurationManager configurationManager = new ConfigurationManager();
		configurationManager.addContainerProvider(new XWorkConfigurationProvider());
		configurationManager.getConfiguration().getContainer().inject(ognlValueStackFactory);
		ValueStack stack = ognlValueStackFactory.createValueStack();
		stack.push(searchContainer.getContext());

		if (searchContext.isObject()) {
			injectParameter(stack, searchContainer.getContext(), target + ".id", value);
		} else {
			injectParameter(stack, searchContainer.getContext(), target, value);
		}

		return stack.findValue(target);
	}

	private boolean acceptableName(String name) {
		if (name.indexOf('=') != -1 || name.indexOf(',') != -1 || name.indexOf('#') != -1 || name.indexOf(':') != -1) {
			return false;
		} else {
			return true;
		}
	}

	public static class SearchCriteriaSessionStore implements PreResultListener {

		private String searchCriteriaKey;

		public SearchCriteriaSessionStore(String searchCriteriaKey) {
			this.searchCriteriaKey = searchCriteriaKey;
		}

		@SuppressWarnings("unchecked")
		@Override
		public void beforeResult(ActionInvocation invocation, String resultCode) {

			if (invocation.getAction() instanceof SearchActionSupport) {

				SearchActionSupport action = (SearchActionSupport) invocation.getAction();
				action.resetSearchCriteria();

				HttpServletRequest request = ServletActionContext.getRequest();
				HttpSession session = request.getSession();
				session.setAttribute(searchCriteriaKey, action.getSearchCriteria());

				// put into httpSession for those should be cleaned
				Set<String> criteriaList;
				if (session.getAttribute("searchCriteriaList") != null) {
					criteriaList = (Set<String>) session.getAttribute("searchCriteriaList");
				} else {
					criteriaList = new HashSet<String>();
				}

				criteriaList.add(searchCriteriaKey);
				session.setAttribute("searchCriteriaList", criteriaList);

				// set search parameters to request for page show
				SearchComponent searchComponent = action.getSearchComponent();
				request.setAttribute("searchParameters", searchComponent.getReturnedParameters());
				request.setAttribute("orderParameters", searchComponent.getOrderParameters());
				request.setAttribute("aliasOrderParameters", searchComponent.getAliasOrderParameters());
			}
		}
	}

}
