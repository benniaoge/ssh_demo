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
public interface GenericDaoSupport {
	
	public static final List<Class<?>> BASIC_CONVERSION_TYPES = Arrays.asList(new Class<?>[] { String.class, Integer.class, Long.class, Date.class });
	
	// --------------------------
	// Hibernate Operations
	// --------------------------
	
	public Serializable save(Object entity);
	
	public void update(Object entity);
	
	public void saveOrUpdate(Object entity);
	
	public void delete(Object entity);
	
	public void delete(Class<?> entityClass, Serializable id);
	
	public <T> T load(Class<T> entityClass, Serializable id);
	
	public <T> List<T> loadAll(Class<T> entityClass);
	
	public <T> T get(Class<T> entityClass, Serializable id);
	
	public int count(Class<?> entityClass);
	
	public <T> List<T> list(Class<T> entityClass, int firstResult, int maxResults);
	
	public int searchForInt(String sentence, Map<String, Object> parameters);
	
	public List<?> searchForList(String sentence, Map<String, Object> parameters);
	
	public List<?> searchForList(String sentence, Map<String,Object> parameters, int firstResult, int maxResults);
	
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
	
	public <T> List<T> queryForList(String sentence, Map<String,Object> parameters, Class<T> resultClass);
	
	public <T> List<T> queryForList(String sentence, Object parameterBean, Class<T> resultClass);
	
	public <T> List<T> queryForList(String sentence, Map<String,Object> parameters, Class<T> resultClass, int beginIndex, int maxResult);
	
	public <T> List<T> queryForList(String sentence, Object parameterBean, Class<T> resultClass, int beginIndex, int maxResult);
	
}
