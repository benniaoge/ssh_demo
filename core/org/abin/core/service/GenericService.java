/**
 * 
 */
package org.abin.core.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.abin.core.page.Page;
import org.abin.core.page.Result;
import org.abin.core.search.SearchComponent;

/**
 * @author ZhangBin
 *
 */
public interface GenericService<T> {

	public Serializable save(T entity);
	
	public void update(T entity);
	
	public void saveOrUpdate(T entity);
	
	public void delete(T entity);
	
	public void delete(Class<T> entityClass, Serializable id);
	
	public T load(Class<T> entityClass, Serializable id);
	
	public T get(Class<T> entityClass, Serializable id);
	
	public List<T> loadAll(Class<T> entityClass);
	
	public int count(Class<T> entityClass);
	
	public List<T> list(Class<T> entityClass, int firstResult, int maxResult);
	
	public Result<T> listByPage(Class<T> entityClass, Page page);
	
	public int searchForInt(String sentence, Map<String, Object> parameters);

	public List<T> searchForList(String sentence, Map<String, Object> parameters);

	public List<T> searchForList(String sentence, Map<String, Object> parameters, int firstResult, int maxResult);

	public Result<T> searchByPage(String sentence, Map<String, Object> parameters, Page page);

	public List<T> searchForList(String sentence, SearchComponent searchComponent);

	public Result<T> searchByPage(String sentence, SearchComponent searchComponent, Page page);
	
	public int queryForInt(String sentence, Map<String, Object> parameters);
	
	public int queryForInt(String sentence, Object parameterBean);

	public List<Map<String,Object>> queryForList(String sentence, Map<String, Object> parameters);

	public List<Map<String,Object>> queryForList(String sentence, Map<String, Object> parameters, int firstResult, int maxResult);

	public Result<Map<String,Object>> queryByPage(String sentence, Map<String, Object> parameters, Page page);

	public List<Map<String,Object>> queryForList(String sentence, Object parameterBean);

	public List<Map<String,Object>> queryForList(String sentence, Object parameterBean, int firstResult, int maxResult);

	public Result<Map<String,Object>> queryByPage(String sentence, Object parameterBean, Page page);

	public List<Map<String, Object>> queryForList(String sentence, SearchComponent searchComponent);

	public Result<Map<String,Object>> queryByPage(String sentence, SearchComponent searchComponent, Page page);
	
	public Result<T> queryByPage(String sentence, Object parameterBean, Class<T> resultClass, Page page);
	
	public List<T> queryForList(String sentence, Object parameterBean, Class<T> resultClass, int beginIndex, int maxResult);
	
	public List<T> queryForList(String sentence, Object parameterBean, Class<T> resultClass);
	
	public List<T> queryForList(String sentence, Map<String,Object> parameters, Class<T> resultClass, Page page);
	
	public List<T> queryForList(String sentence, Map<String,Object> parameters, Class<T> resultClass, int beginIndex, int maxResult);
	
	public List<T> queryForList(String sentence, Map<String,Object> parameters, Class<T> resultClass);
	
	public Map<String, Object> queryForSingle(String sentence, Map<String, Object> parameters);
	
}
