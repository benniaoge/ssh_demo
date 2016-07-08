package com.abin.demo.action.demo;

import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.abin.demo.action.BaseAction;
import com.abin.demo.demo.entity.DemoEntity;
import com.abin.demo.demo.service.DemoService;

@Result(name="search",location="demo-search.do",type="redirect")
public class DemoCreateAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private DemoService demoService;
	
	private DemoEntity demo;
	
	
	@Override
	public String execute() throws Exception {
		demoService.save(demo);
		return "search";
	}
	
	public DemoEntity getDemo() {
		return demo;
	}
	
	public void setDemo(DemoEntity demo) {
		this.demo = demo;
	}
	
	public DemoService getDemoService() {
		return demoService;
	}
	
	@Autowired
	public void setDemoService(DemoService demoService) {
		this.demoService = demoService;
	}

}
