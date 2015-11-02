/**
 * 
 */
package org.abin.core.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.abin.core.dao.GenericDaoSupport;
import org.abin.core.page.Page;
import org.abin.core.page.PageUtils;
import org.abin.core.page.Result;
import org.abin.core.search.SearchComponent;
import org.abin.core.search.SearchUtils;
import org.abin.core.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author ZhangBin
 * 
 */
public class GenericServiceImpl implements GenericService {

	private GenericDaoSupport genericDaoSupport;

	@Override
	public Serializable save(Object entity) {
		return genericDaoSupport.save(entity);
	}

	@Override
	public void update(Object entity) {
		genericDaoSupport.update(entity);
	}

	@Override
	public void saveOrUpdate(Object entity) {
		genericDaoSupport.saveOrUpdate(entity);
	}

	@Override
	public void delete(Object entity) {
		genericDaoSupport.delete(entity);
	}

	@Override
	public void delete(Class<?> entityClass, Serializable id) {
		genericDaoSupport.delete(entityClass, id);
	}

	@Override
	public <T> T load(Class<T> entityClass, Serializable id) {
		return genericDaoSupport.load(entityClass, id);
	}

	@Override
	public <T> T get(Class<T> entityClass, Serializable id) {
		return genericDaoSupport.get(entityClass, id);
	}

	@Override
	public <T> List<T> loadAll(Class<T> entityClass) {
		return genericDaoSupport.loadAll(entityClass);
	}

	@Override
	public int count(Class<?> entityClass) {
		return genericDaoSupport.count(entityClass);
	}

	@Override
	public <T> List<T> list(Class<T> entityClass, int firstResult, int maxResults) {
		return genericDaoSupport.list(entityClass, firstResult, maxResults);
	}

	@Override
	public <T> Result listByPage(Class<T> entityClass, Page page) {
		List<T> result = new ArrayList<T>();
		int totalRecords = genericDaoSupport.count(entityClass);
		if (totalRecords > 0) {
			page = PageUtils.createPage(page, totalRecords);
			result = genericDaoSupport.list(entityClass, page.getBeginIndex(), page.getEveryPage());
		}
		return new Result(page, result);
	}

	@Override
	public int searchForInt(String sentence, Map<String, Object> parameters) {
		return genericDaoSupport.searchForInt(sentence, parameters);
	}

	@Override
	public List<?> searchForList(String sentence, Map<String, Object> parameters) {
		return genericDaoSupport.searchForList(sentence, parameters);
	}

	@Override
	public List<?> searchForList(String sentence, Map<String, Object> parameters, int firstResult, int maxResults) {
		return genericDaoSupport.searchForList(sentence, parameters, firstResult, maxResults);
	}

	@Override
	public Result searchByPage(String sentence, Map<String, Object> parameters, Page page) {
		List<?> result = new ArrayList<Object>();
		int totalRecords = genericDaoSupport.searchForInt(SearchUtils.getHQLCountSentence(sentence), parameters);
		if (totalRecords > 0) {
			page = PageUtils.createPage(page, totalRecords);
			result = genericDaoSupport.searchForList(sentence, parameters, page.getBeginIndex(), page.getEveryPage());
		}
		return new Result(page, result);
	}

	@Override
	public List<?> searchForList(String sentence, SearchComponent searchComponent) {
		return searchForList(searchComponent.getSearchSentence(sentence, true), searchComponent.getParameters());
	}

	@Override
	public Result searchByPage(String sentence, SearchComponent searchComponent, Page page) {
		List<?> result = new ArrayList<Object>();
		int totalRecords = searchForInt(searchComponent.getHQLCountSentence(sentence, true), searchComponent.getParameters());
		if (totalRecords > 0) {
			page = PageUtils.createPage(page, totalRecords);
			result = genericDaoSupport.searchForList(searchComponent.getSearchSentence(sentence, true), searchComponent.getParameters(),
					page.getBeginIndex(), page.getEveryPage());
		}
		return new Result(page, result);
	}

	@Override
	public int queryForInt(String sentence, Map<String, Object> parameters) {
		return genericDaoSupport.queryForInt(sentence, parameters);
	}

	@Override
	public int queryForInt(String sentence, Object parameterBean) {
		return genericDaoSupport.queryForInt(sentence, parameterBean);
	}

	@Override
	public List<Map<String, Object>> queryForList(String sentence, Map<String, Object> parameters) {
		return genericDaoSupport.queryForList(sentence, parameters);
	}

	@Override
	public List<Map<String, Object>> queryForList(String sentence, Map<String, Object> parameters, int firstResult, int maxResults) {
		return genericDaoSupport.queryForList(sentence, parameters, firstResult, maxResults);
	}

	@Override
	public Result queryByPage(String sentence, Map<String, Object> parameters, Page page) {
		List<?> result = new ArrayList<Object>();
		int totalRecords = genericDaoSupport.queryForInt(SearchUtils.getSQLCountSentence(sentence), parameters);
		if (totalRecords > 0) {
			page = PageUtils.createPage(page, totalRecords);
			result = genericDaoSupport.queryForList(sentence, parameters, page.getBeginIndex(), page.getEveryPage());
		}
		return new Result(page, result);
	}

	@Override
	public List<Map<String, Object>> queryForList(String sentence, Object parameterBean) {
		return genericDaoSupport.queryForList(sentence, parameterBean);
	}

	@Override
	public List<Map<String, Object>> queryForList(String sentence, Object parameterBean, int firstResult, int maxResults) {
		return genericDaoSupport.queryForList(sentence, parameterBean, firstResult, maxResults);
	}

	@Override
	public Result queryByPage(String sentence, Object parameterBean, Page page) {
		List<?> result = new ArrayList<Object>();
		int totalRecords = genericDaoSupport.queryForInt(SearchUtils.getSQLCountSentence(sentence), parameterBean);
		if (totalRecords > 0) {
			page = PageUtils.createPage(page, totalRecords);
			result = genericDaoSupport.queryForList(sentence, parameterBean, page.getBeginIndex(), page.getEveryPage());
		}
		return new Result(page, result);
	}

	@Override
	public List<?> queryForList(String sentence, SearchComponent searchComponent) {
		return genericDaoSupport.queryForList(searchComponent.getSearchSentence(sentence, true), searchComponent.getParameters());
	}

	@Override
	public Result queryByPage(String sentence, SearchComponent searchComponent, Page page) {
		List<?> result = new ArrayList<Object>();
		int totalRecords = genericDaoSupport.queryForInt(searchComponent.getSQLCountSentence(sentence, true), searchComponent.getParameters());
		if (totalRecords > 0) {
			page = PageUtils.createPage(page, totalRecords);
			result = genericDaoSupport.queryForList(searchComponent.getSearchSentence(sentence, true), searchComponent.getParameters(),
					page.getBeginIndex(), page.getEveryPage());
		}
		return new Result(page, result);
	}
	
	@Override
	public <T> Result queryByPage(String sentence, Object parameterBean, Class<T> resultClass, Page page) {
		List<T> result = new ArrayList<T>();
		int totalRecords = genericDaoSupport.queryForInt(SearchUtils.getSQLCountSentence(sentence), parameterBean);
		if (totalRecords > 0) {
			page = PageUtils.createPage(page, totalRecords);
			result = genericDaoSupport.queryForList(sentence, parameterBean, resultClass, page.getBeginIndex(), page.getEveryPage());
		}
		return new Result(page, result);
	}
	
	@Override
	public <T> List<T> queryForList(String sentence, Map<String, Object> parameters, Class<T> resultClass) {
		return genericDaoSupport.queryForList(sentence, parameters, resultClass);
	}
	
	@Override
	public <T> List<T> queryForList(String sentence, Map<String, Object> parameters, Class<T> resultClass, int beginIndex, int maxResult) {
		return genericDaoSupport.queryForList(sentence, parameters, resultClass, beginIndex, maxResult);
	}
	
	@Override
	public <T> List<T> queryForList(String sentence, Map<String, Object> parameters, Class<T> resultClass, Page page) {
		List<T> result = new ArrayList<T>();
		int totalRecords = genericDaoSupport.queryForInt(SearchUtils.getSQLCountSentence(sentence), parameters);
		if (totalRecords > 0) {
			page = PageUtils.createPage(page, totalRecords);
			result = genericDaoSupport.queryForList(sentence, parameters, resultClass, page.getBeginIndex(), page.getEveryPage());
		}
		return result;
	}
	
	@Override
	public <T> List<T> queryForList(String sentence, Object parameterBean, Class<T> resultClass) {
		return genericDaoSupport.queryForList(sentence, parameterBean, resultClass);
	}
	
	@Override
	public <T> List<T> queryForList(String sentence, Object parameterBean, Class<T> resultClass, int beginIndex, int maxResult) {
		return genericDaoSupport.queryForList(sentence, parameterBean, resultClass, beginIndex, maxResult);
	}
	
	@Override
	public Map<String, Object> queryForSingle(String sentence, Map<String, Object> parameters) {
		return genericDaoSupport.queryForSingle(sentence, parameters);
	}
	
	public GenericDaoSupport getGenericDaoSupport() {
		return genericDaoSupport;
	}

	@Autowired
	public void setGenericDaoSupport(@Qualifier("genericDaoSupport") GenericDaoSupport genericDaoSupport) {
		this.genericDaoSupport = genericDaoSupport;
	}

}
