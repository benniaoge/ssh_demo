package org.abin.core.struts.action;

import org.abin.core.page.Result;
import org.abin.core.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;


public class SearchAction extends SearchActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private GenericService genericService;

	private String sentenceTemplate;

	private Class<?> entityClass;

	private boolean pagination;

	@Override
	public String execute() throws Exception {
		if (pagination) {
			Result tempResult = genericService.listByPage(entityClass, getPage());
			result = tempResult.getItems();
			page = tempResult.getPage();
		} else {
			result = genericService.loadAll(entityClass);
		}
		return SUCCESS;
	}

	@Override
	public void resetCustomerSearchCriteria() {
		searchCriteria.put("sentenceTemplate", sentenceTemplate);
		searchCriteria.put("entityClass", entityClass);
		searchCriteria.put("pagination", pagination);
	}
	
	public GenericService getGenericService() {
		return genericService;
	}

	@Autowired
	public void setGenericService(GenericService genericService) {
		this.genericService = genericService;
	}

}
