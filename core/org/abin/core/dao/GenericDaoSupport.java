package org.abin.core.dao;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author ZhangBin
 * 
 */
public interface GenericDaoSupport<T> {
	
	public static final List<Class<?>> BASIC_CONVERSION_TYPES = Arrays.asList(new Class<?>[] { String.class, Integer.class, Long.class, Date.class });
	
	// --------------------------
	// Hibernate Operations
	// --------------------------
	
	public Serializable save(T entity);
	
	public void update(T entity);
	
	public void saveOrUpdate(T entity);
	
	public void delete(T entity);
	
	public void delete(Class<T> entityClass, Serializable id);
	
	public T load(Class<T> entityClass, Serializable id);
	
	public List<T> loadAll(Class<T> entityClass);
	
	public T get(Class<T> entityClass, Serializable id);
	
	public int count(Class<T> entityClass);
	
	public List<T> list(Class<T> entityClass, int firstResult, int maxResults);
	
	public int searchForInt(String sentence, Map<String, Object> parameters);
	
	public List<T> searchForList(String sentence, Map<String, Object> parameters);
	
	public List<T> searchForList(String sentence, Map<String,Object> parameters, int firstResult, int maxResults);
	
	// --------------------------
	// JDBC Operations
	// --------------------------
	
	public int queryForInt(String sentence, Map<String, Object> parameters);

	public int queryForInt(String sentence, Object parameterBean);

	public List<Map<String, Object>> queryForList(String sentence, Map<String, Object> parameters);

	public List<Map<String, Object>> queryForList(String sentence, Map<String, Object> parameters, int firstResult, int maxResults);
	
	public List<Map<String, Object>> queryForList(String sentence, Object parameterBean);

	public List<Map<String, Object>> queryForList(String sentence, Object parameterBean, int firstResult, int maxResults);

	public Map<String, Object> queryForSingle(String sentence, Map<String, Object> parameters);
	
	public List<T> queryForList(String sentence, Map<String,Object> parameters, Class<T> resultClass);
	
	public List<T> queryForList(String sentence, Object parameterBean, Class<T> resultClass);
	
	public List<T> queryForList(String sentence, Map<String,Object> parameters, Class<T> resultClass, int beginIndex, int maxResult);
	
	public List<T> queryForList(String sentence, Object parameterBean, Class<T> resultClass, int beginIndex, int maxResult);
	
}
