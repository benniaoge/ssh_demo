package org.bng.core.struts.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bng.core.page.Page;
import org.bng.core.search.SearchComponent;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class SearchActionSupport extends BaseActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected Map<String, Object> searchCriteria;
	
	protected SearchComponent searchComponent = new SearchComponent();
	
	protected Page page;
	
	protected List<?> result;
	
	private String displayName;
	
	public abstract void resetCustomerSearchCriteria();
	
	public void resetSearchCriteria() {
		if(searchCriteria == null) {
			searchCriteria = new HashMap<String, Object>();
		}
		searchCriteria.put("searchComponent", searchComponent);
		resetCustomerSearchCriteria();
	}
	
	public Map<String, Object> getSearchCriteria() {
		return searchCriteria;
	}

	public void setSearchCriteria(Map<String, Object> searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	public SearchComponent getSearchComponent() {
		return searchComponent;
	}

	public void setSearchComponent(SearchComponent searchComponent) {
		this.searchComponent = searchComponent;
	}

	public Page getPage() {
		return page;
	}

	@Autowired
	public void setPage(Page page) {
		this.page = page;
	}

	public List<?> getResult() {
		return result;
	}

	public void setResult(List<?> result) {
		this.result = result;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
}
