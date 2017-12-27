package org.bng.core.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Result<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Page page;
	
	private List<T> items;
	
	public Result(Page page, List<T> items) {
		this.page = page;
		this.items = items;
	}
	
	public Result(Page page, Set<T> items){
		this.page = page;
		List<T> tempList = new ArrayList<T>();
		tempList.addAll(items);
		this.items = tempList;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

}
