/**
 * 
 */
package org.bng.core.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bng.core.dao.GenericDaoSupport;
import org.bng.core.page.Page;
import org.bng.core.page.PageUtils;
import org.bng.core.page.Result;
import org.bng.core.search.SearchComponent;
import org.bng.core.search.SearchUtils;
import org.bng.core.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author ZhangBin
 * 
 */
public class GenericServiceImpl<T> implements GenericService<T> {

	private GenericDaoSupport<T> genericDaoSupport;

	@Override
	public Serializable save(T entity) {
		return genericDaoSupport.save(entity);
	}

	@Override
	public void update(T entity) {
		genericDaoSupport.update(entity);
	}

	@Override
	public void saveOrUpdate(T entity) {
		genericDaoSupport.saveOrUpdate(entity);
	}

	@Override
	public void delete(T entity) {
		genericDaoSupport.delete(entity);
	}

	@Override
	public void delete(Class<T> entityClass, Serializable id) {
		genericDaoSupport.delete(entityClass, id);
	}

	@Override
	public T load(Class<T> entityClass, Serializable id) {
		return genericDaoSupport.load(entityClass, id);
	}

	@Override
	public T get(Class<T> entityClass, Serializable id) {
		return genericDaoSupport.get(entityClass, id);
	}

	@Override
	public List<T> loadAll(Class<T> entityClass) {
		return genericDaoSupport.loadAll(entityClass);
	}

	@Override
	public int count(Class<T> entityClass) {
		return genericDaoSupport.count(entityClass);
	}

	@Override
	public List<T> list(Class<T> entityClass, int firstResult, int maxResults) {
		return genericDaoSupport.list(entityClass, firstResult, maxResults);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Result<T> listByPage(Class<T> entityClass, Page page) {
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
	public List<T> searchForList(String sentence, Map<String, Object> parameters) {
		return genericDaoSupport.searchForList(sentence, parameters);
	}

	@Override
	public List<T> searchForList(String sentence, Map<String, Object> parameters, int firstResult, int maxResults) {
		return genericDaoSupport.searchForList(sentence, parameters, firstResult, maxResults);
	}

	@Override
	public Result<T> searchByPage(String sentence, Map<String, Object> parameters, Page page) {
		List<T> result = new ArrayList<T>();
		int totalRecords = genericDaoSupport.searchForInt(SearchUtils.getHQLCountSentence(sentence), parameters);
		if (totalRecords > 0) {
			page = PageUtils.createPage(page, totalRecords);
			result = genericDaoSupport.searchForList(sentence, parameters, page.getBeginIndex(), page.getEveryPage());
		}
		return new Result<T>(page, result);
	}

	@Override
	public List<T> searchForList(String sentence, SearchComponent searchComponent) {
		return searchForList(searchComponent.getSearchSentence(sentence, true), searchComponent.getParameters());
	}

	@Override
	public Result<T> searchByPage(String sentence, SearchComponent searchComponent, Page page) {
		List<T> result = new ArrayList<T>();
		int totalRecords = searchForInt(searchComponent.getHQLCountSentence(sentence, true), searchComponent.getParameters());
		if (totalRecords > 0) {
			page = PageUtils.createPage(page, totalRecords);
			result = genericDaoSupport.searchForList(searchComponent.getSearchSentence(sentence, true), searchComponent.getParameters(),
					page.getBeginIndex(), page.getEveryPage());
		}
		return new Result<T>(page, result);
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
	public Result<Map<String,Object>> queryByPage(String sentence, Map<String, Object> parameters, Page page) {
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		int totalRecords = genericDaoSupport.queryForInt(SearchUtils.getSQLCountSentence(sentence), parameters);
		if (totalRecords > 0) {
			page = PageUtils.createPage(page, totalRecords);
			result = genericDaoSupport.queryForList(sentence, parameters, page.getBeginIndex(), page.getEveryPage());
		}
		return new Result<Map<String,Object>>(page, result);
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
	public Result<Map<String,Object>> queryByPage(String sentence, Object parameterBean, Page page) {
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		int totalRecords = genericDaoSupport.queryForInt(SearchUtils.getSQLCountSentence(sentence), parameterBean);
		if (totalRecords > 0) {
			page = PageUtils.createPage(page, totalRecords);
			result = genericDaoSupport.queryForList(sentence, parameterBean, page.getBeginIndex(), page.getEveryPage());
		}
		return new Result<Map<String,Object>>(page, result);
	}

	@Override
	public List<Map<String, Object>> queryForList(String sentence, SearchComponent searchComponent) {
		return genericDaoSupport.queryForList(searchComponent.getSearchSentence(sentence, true), searchComponent.getParameters());
	}

	@Override
	public Result<Map<String,Object>> queryByPage(String sentence, SearchComponent searchComponent, Page page) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		int totalRecords = genericDaoSupport.queryForInt(searchComponent.getSQLCountSentence(sentence, true), searchComponent.getParameters());
		if (totalRecords > 0) {
			page = PageUtils.createPage(page, totalRecords);
			result = genericDaoSupport.queryForList(searchComponent.getSearchSentence(sentence, true), searchComponent.getParameters(),
					page.getBeginIndex(), page.getEveryPage());
		}
		return new Result<Map<String,Object>>(page, result);
	}
	
	@Override
	public Result<T> queryByPage(String sentence, Object parameterBean, Class<T> resultClass, Page page) {
		List<T> result = new ArrayList<T>();
		int totalRecords = genericDaoSupport.queryForInt(SearchUtils.getSQLCountSentence(sentence), parameterBean);
		if (totalRecords > 0) {
			page = PageUtils.createPage(page, totalRecords);
			result = genericDaoSupport.queryForList(sentence, parameterBean, resultClass, page.getBeginIndex(), page.getEveryPage());
		}
		return new Result<T>(page, result);
	}
	
	@Override
	public List<T> queryForList(String sentence, Map<String, Object> parameters, Class<T> resultClass) {
		return genericDaoSupport.queryForList(sentence, parameters, resultClass);
	}
	
	@Override
	public List<T> queryForList(String sentence, Map<String, Object> parameters, Class<T> resultClass, int beginIndex, int maxResult) {
		return genericDaoSupport.queryForList(sentence, parameters, resultClass, beginIndex, maxResult);
	}
	
	@Override
	public List<T> queryForList(String sentence, Map<String, Object> parameters, Class<T> resultClass, Page page) {
		List<T> result = new ArrayList<T>();
		int totalRecords = genericDaoSupport.queryForInt(SearchUtils.getSQLCountSentence(sentence), parameters);
		if (totalRecords > 0) {
			page = PageUtils.createPage(page, totalRecords);
			result = genericDaoSupport.queryForList(sentence, parameters, resultClass, page.getBeginIndex(), page.getEveryPage());
		}
		return result;
	}
	
	@Override
	public List<T> queryForList(String sentence, Object parameterBean, Class<T> resultClass) {
		return genericDaoSupport.queryForList(sentence, parameterBean, resultClass);
	}
	
	@Override
	public List<T> queryForList(String sentence, Object parameterBean, Class<T> resultClass, int beginIndex, int maxResult) {
		return genericDaoSupport.queryForList(sentence, parameterBean, resultClass, beginIndex, maxResult);
	}
	
	@Override
	public Map<String, Object> queryForSingle(String sentence, Map<String, Object> parameters) {
		return genericDaoSupport.queryForSingle(sentence, parameters);
	}
	
	public GenericDaoSupport<T> getGenericDaoSupport() {
		return genericDaoSupport;
	}
	
	@Autowired
	public void setGenericDaoSupport(@Qualifier("genericDaoSupport")GenericDaoSupport<T> genericDaoSupport) {
		this.genericDaoSupport = genericDaoSupport;
	}

}
