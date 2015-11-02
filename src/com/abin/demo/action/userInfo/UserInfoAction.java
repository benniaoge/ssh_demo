package com.abin.demo.action.userInfo;

import org.abin.core.page.Result;
import org.abin.core.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.abin.demo.action.BaseSearchAction;
import com.abin.demo.dog.entity.Dog;

public class UserInfoAction extends BaseSearchAction {

	private static final long serialVersionUID = 1L;

	private GenericService genericService;

	private Dog dog;

	@Override
	public String execute() throws Exception {
		System.out.println("=======================");
		String sentenceTemplate = "FROM Dog dog WHERE 1=1";
		Result tempResult = genericService.searchByPage(sentenceTemplate, searchComponent, page);
		this.result = tempResult.getItems();
		this.page = tempResult.getPage();
		return SUCCESS;
	}

	public String add() throws Exception {
		return "add";
	}

	public String save() throws Exception {
		genericService.save(dog);
		return execute();
	}

	@Override
	public void resetCustomerSearchCriteria() {
	}

	public GenericService getGenericService() {
		return genericService;
	}

	@Autowired
	public void setGenericService(@Qualifier("genericService") GenericService genericService) {
		this.genericService = genericService;
	}

	public Dog getDog() {
		return dog;
	}

	public void setDog(Dog dog) {
		this.dog = dog;
	}

}
