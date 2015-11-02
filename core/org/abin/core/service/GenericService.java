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
public interface GenericService {

	public Serializable save(Object entity);
	
	public void update(Object entity);
	
	public void saveOrUpdate(Object entity);
	
	public void delete(Object entity);
	
	public void delete(Class<?> entityClass, Serializable id);
	
	public <T> T load(Class<T> entityClass, Serializable id);
	
	public <T> T get(Class<T> entityClass, Serializable id);
	
	public <T> List<T> loadAll(Class<T> entityClass);
	
	public int count(Class<?> entityClass);
	
	public <T> List<T> list(Class<T> entityClass, int firstResult, int maxResult);
	
	public <T> Result listByPage(Class<T> entityClass, Page page);
	
	public int searchForInt(String sentence, Map<String, Object> parameters);

	public List<?> searchForList(String sentence, Map<String, Object> parameters);

	public List<?> searchForList(String sentence, Map<String, Object> parameters, int firstResult, int maxResult);

	public Result searchByPage(String sentence, Map<String, Object> parameters, Page page);

	public List<?> searchForList(String sentence, SearchComponent searchComponent);

	public Result searchByPage(String sentence, SearchComponent searchComponent, Page page);
	
	public int queryForInt(String sentence, Map<String, Object> parameters);
	
	public int queryForInt(String sentence, Object parameterBean);

	public List<Map<String,Object>> queryForList(String sentence, Map<String, Object> parameters);

	public List<Map<String,Object>> queryForList(String sentence, Map<String, Object> parameters, int firstResult, int maxResult);

	public Result queryByPage(String sentence, Map<String, Object> parameters, Page page);

	public List<Map<String,Object>> queryForList(String sentence, Object parameterBean);

	public List<Map<String,Object>> queryForList(String sentence, Object parameterBean, int firstResult, int maxResult);

	public Result queryByPage(String sentence, Object parameterBean, Page page);

	public List<?> queryForList(String sentence, SearchComponent searchComponent);

	public Result queryByPage(String sentence, SearchComponent searchComponent, Page page);
	
	public <T> Result queryByPage(String sentence, Object parameterBean, Class<T> resultClass, Page page);
	
	public <T> List<T> queryForList(String sentence, Object parameterBean, Class<T> resultClass, int beginIndex, int maxResult);
	
	public <T> List<T> queryForList(String sentence, Object parameterBean, Class<T> resultClass);
	
	public <T> List<T> queryForList(String sentence, Map<String,Object> parameters, Class<T> resultClass, Page page);
	
	public <T> List<T> queryForList(String sentence, Map<String,Object> parameters, Class<T> resultClass, int beginIndex, int maxResult);
	
	public <T> List<T> queryForList(String sentence, Map<String,Object> parameters, Class<T> resultClass);
	
	public Map<String, Object> queryForSingle(String sentence, Map<String, Object> parameters);
	
}
