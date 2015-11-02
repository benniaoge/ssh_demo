package org.abin.core.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Result implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Page page;
	
	private List<?> items;
	
	public Result(Page page, List<?> items) {
		this.page = page;
		this.items = items;
	}
	
	public Result(Page page, Set<Object> items){
		this.page = page;
		List<Object> tempList = new ArrayList<Object>();
		tempList.addAll(items);
		this.items = tempList;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public List<?> getItems() {
		return items;
	}

	public void setItems(List<?> items) {
		this.items = items;
	}
	
	

}
