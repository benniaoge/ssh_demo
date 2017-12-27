package com.abin.demo.action.demo;

import java.util.List;

import org.bng.core.page.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.abin.demo.action.BaseSearchAction;
import com.abin.demo.demo.entity.DemoEntity;
import com.abin.demo.demo.service.DemoService;

public class DemoSearchAction extends BaseSearchAction {

	private static final long serialVersionUID = 1L;
	
	private DemoService demoService;
	
	private List<DemoEntity> demoList;
	
	@Override
	public String execute() throws Exception {
		Result<DemoEntity> r = demoService.searchByPage("FROM DemoEntity demo WHERE 1=1", searchComponent, page);
		page = r.getPage();
		demoList = r.getItems();
		return SUCCESS;
	}
	
	@Override
	public void resetCustomerSearchCriteria() {
	}
	
	public List<DemoEntity> getDemoList() {
		return demoList;
	}
	
	public void setDemoList(List<DemoEntity> demoList) {
		this.demoList = demoList;
	}
	
	public DemoService getDemoService() {
		return demoService;
	}
	
	@Autowired
	public void setDemoService(DemoService demoService) {
		this.demoService = demoService;
	}

}
