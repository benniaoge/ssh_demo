package com.abin.demo.action.dog;

import java.util.HashMap;
import java.util.Map;

import org.abin.core.page.Result;
import org.abin.core.service.GenericService;
import org.abin.core.util.VelocityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.abin.demo.action.BaseSearchAction;
import com.abin.demo.dog.entity.Dog;

public class DogAction extends BaseSearchAction {

	private static final long serialVersionUID = 1L;
	
	private GenericService genericService;
	
	private Dog dog;
	
	private String test;
	
	public String getTest() {
		return test;
	}
	
	public void setTest(String test) {
		this.test = test;
	}
	
	public String t() {
		test = "2";
		return "t";
	}
	
	public String test() throws Exception {
		result = genericService.loadAll(Dog.class);
		
		Map<String,Object> root = new HashMap<String, Object>();
		
		root.put("dogs", result);
		String content = VelocityUtils.processTemplateFile(root, "dog.vm");
		
		System.out.println(content);
		System.out.println(result.size());
		
		return null;
	}
	
	/**
	 * loadAll
	 */
	@Override
	public String execute() throws Exception {
		Result r = genericService.searchByPage("FROM Dog dog WHERE 1=1", searchComponent, page);
		page = r.getPage();
		result = r.getItems();
		return SUCCESS;
	}
	
	/**
	 * listByPage
	 * @return
	 * @throws Exception
	 */
	public String list1() throws Exception {
		Result tempResult = genericService.listByPage(Dog.class, page);
		result = tempResult.getItems();
		page = tempResult.getPage();
		return SUCCESS;
	}
	
	/**
	 * searchByPage
	 * @return
	 * @throws Exception
	 */
	public String search1() throws Exception {
		String sentenceTemplate = "FROM Dog dog WHERE 1=1";
		Result tempResult = genericService.searchByPage(sentenceTemplate, searchComponent, page);
		result = tempResult.getItems();
		page = tempResult.getPage();
		return SUCCESS;
	}
	
	/**
	 * searchByPage
	 * @return
	 * @throws Exception
	 */
	public String search2() throws Exception {
		String sentence = "SELECT * FROM Dog dog WHERE 1=1";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("eq.dog.name", "wangcai");
		Result tempResult = genericService.searchByPage(sentence, parameters, page);
		result = tempResult.getItems();
		page = tempResult.getPage();
		return SUCCESS;
	}
	
		
	@Override
	public void resetCustomerSearchCriteria() {
	}
	
	public GenericService getGenericService() {
		return genericService;
	}
	
	@Autowired
	public void setGenericService(GenericService genericService) {
		this.genericService = genericService;
	}
	
	public Dog getDog() {
		return dog;
	}
	
	public void setDog(Dog dog) {
		this.dog = dog;
	}

}
