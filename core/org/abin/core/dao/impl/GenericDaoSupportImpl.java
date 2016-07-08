/**
 * 
 */
package org.abin.core.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.abin.core.dao.GenericDaoSupport;
import org.abin.core.search.SearchUtils;
import org.abin.core.search.dialect.SearchDialect;
import org.abin.core.util.BeanUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


/**
 * @author ZhangBin
 * 
 */
public class GenericDaoSupportImpl<T> extends HibernateDaoSupport implements GenericDaoSupport<T> {

	private SimpleJdbcTemplate simpleJdbcTemplate;

	private SearchDialect searchDialect;

	// ----------------------------
	// Generic Hibernate Operations
	// ----------------------------

	@Override
	public Serializable save(T entity) {
		return getHibernateTemplate().save(entity);
	}

	@Override
	public void update(T entity) {
		getHibernateTemplate().update(entity);
	}

	@Override
	public void saveOrUpdate(T entity) {
		getHibernateTemplate().saveOrUpdate(entity);
	}

	@Override
	public void delete(T entity) {
		getHibernateTemplate().delete(entity);
	}

	@Override
	public void delete(Class<T> entityClass, Serializable id) {
		delete(load(entityClass, id));
	}
	
	@Override
	public T load(Class<T> entityClass, Serializable id) {
		return getHibernateTemplate().load(entityClass, id);
	}
	
	@Override
	public List<T> loadAll(Class<T> entityClass) {
		return getHibernateTemplate().loadAll(entityClass);
	}
	
	@Override
	public T get(Class<T> entityClass, Serializable id) {
		return getHibernateTemplate().get(entityClass, id);
	}
	
	@Override
	public int count(Class<T> entityClass) {
		String querySql = SearchUtils.getCountSentence(entityClass);
		Query query = getSession().createQuery(querySql);
		return ((Long)query.iterate().next()).intValue();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> list(Class<T> entityClass, int firstResult, int maxResults) {
		Query query = getSession().createQuery(SearchUtils.getQuerySentence(entityClass));
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		return query.list();
	}
	
	@Override
	public int searchForInt(String sentence, Map<String,Object> parameters) {
		Query query = getSession().createQuery(sentence);
		for (Iterator<Entry<String, Object>> iter = parameters.entrySet().iterator(); iter.hasNext();) {
			Entry<String,Object> entry = iter.next();
			Object value = entry.getValue();
			if (value instanceof List) {
				query.setParameterList(entry.getKey(), (List<?>)value);
			} else {
				query.setParameter(entry.getKey(), value);
			}
		}
		List<?> result = query.list(); // deal with the condition when hql contains group by
		return result.size() > 1 ? result.size() : ((Long) result.get(0)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> searchForList(String sentence, Map<String, Object> parameters) {
		Query query = getSession().createQuery(sentence);
		for(Iterator<Entry<String, Object>> iter = parameters.entrySet().iterator(); iter.hasNext();) {
			Entry<String,Object> entry = iter.next();
			Object value = entry.getValue();
			if(value instanceof List) {
				query.setParameterList(entry.getKey(), (List<?>)value);
			}else {
				query.setParameter(entry.getKey(), value);
			}
		}
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> searchForList(String sentence, Map<String, Object> parameters, int firstResult, int maxResults) {
		Query query = getSession().createQuery(sentence);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);
		for(Iterator<Entry<String, Object>> iter = parameters.entrySet().iterator(); iter.hasNext();) {
			Entry<String,Object> entry = iter.next();
			Object value = entry.getValue();
			if(value instanceof List) {
				query.setParameterList(entry.getKey(), (List<?>)value);
			}else {
				query.setParameter(entry.getKey(), value);
			}
		}
		return query.list();
	}
	
	// ----------------------------
	// Generic JDBC Operations
	// ----------------------------

	@Override
	public int queryForInt(String sentence, Map<String, Object> parameters) {
		return getSimpleJdbcTemplate().queryForInt(sentence, parameters);
	}

	@Override
	public int queryForInt(String sentence, Object parameterBean) {
		SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(parameterBean);
		return getSimpleJdbcTemplate().queryForInt(sentence, sqlParameterSource);
	}

	@Override
	public List<Map<String, Object>> queryForList(String sentence, Map<String, Object> parameters) {
		return getSimpleJdbcTemplate().queryForList(sentence, parameters);
	}

	@Override
	public List<Map<String, Object>> queryForList(String sentence, Map<String, Object> parameters, int firstResult, int maxResults) {
		parameters.put("firstResult", firstResult);
		parameters.put("maxResults", maxResults);
		return getSimpleJdbcTemplate().queryForList(SearchUtils.getLimitedSentence(searchDialect, sentence, firstResult, maxResults, false), parameters);
	}

	@Override
	public List<Map<String, Object>> queryForList(String sentence, Object parameterBean) {
		SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(parameterBean);
		return getSimpleJdbcTemplate().queryForList(sentence, sqlParameterSource);
	}

	@Override
	public List<Map<String, Object>> queryForList(String sentence, Object parameterBean, int firstResult, int maxResults) {
		SqlParameterSource sqlParameterSource = new BeanPropertySqlParameterSource(parameterBean);
		return getSimpleJdbcTemplate().queryForList(SearchUtils.getLimitedSentence(searchDialect, sentence, firstResult, maxResults, true), sqlParameterSource);
	}

	@Override
	public Map<String, Object> queryForSingle(String sentence, Map<String, Object> parameters) {
		return getSimpleJdbcTemplate().queryForMap(sentence, parameters);
	}
	
	@Override
	public List<T> queryForList(String sentence, Map<String, Object> parameters, Class<T> resultClass) {
		List<Map<String,Object>> tempResult = queryForList(sentence, parameters);
		return getResultList(tempResult, resultClass);
	}
	
	@Override
	public List<T> queryForList(String sentence, Object parameterBean, Class<T> resultClass) {
		List<Map<String,Object>> tempResult = queryForList(sentence, parameterBean);
		return getResultList(tempResult, resultClass);
	}
	
	@Override
	public List<T> queryForList(String sentence, Map<String, Object> parameters, Class<T> resultClass, int beginIndex, int maxResult) {
		List<Map<String,Object>> tempResult = queryForList(sentence, parameters, beginIndex, maxResult);
		return getResultList(tempResult, resultClass);
	}
	
	@Override
	public List<T> queryForList(String sentence, Object parameterBean, Class<T> resultClass, int beginIndex, int maxResult) {
		List<Map<String,Object>> tempResult = queryForList(sentence, parameterBean, beginIndex, maxResult);
		return getResultList(tempResult, resultClass);
	}
	
	private List<T> getResultList(List<Map<String,Object>> tempResult, Class<T> resultClass) {
		List<T> result = new ArrayList<T>();
		for (Map<String,Object> temp : tempResult) {
			T bean = BeanUtils.reflectClass(resultClass);
			BeanUtils.populate(bean, temp);
			result.add(bean);
		}
		return result;
	}
	
	/*
	private <T> List<T> getResultList(List<Map<String,Object>> tempResult, Class<T> resultClass) {
		List<T> result = new ArrayList<T>();
		for (Map<String,Object> temp : tempResult) {
			if (BASIC_CONVERSION_TYPES.contains(temp.values().iterator().next().getClass())) {
				result.add(temp.values().iterator().next());
			} else {
				T bean = BeanUtils.reflectClass(resultClass);
				BeanUtils.populate(bean, temp);
				result.add(bean);
			}
		}
		return result;
	}
	*/
	
	@Autowired
	public void setSessionFactory2(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		initTemplateConfig(dataSource);
	}

	protected void initTemplateConfig(DataSource dataSource) {
		this.simpleJdbcTemplate = new SimpleJdbcTemplate(createJdbcTemplate(dataSource));
	}

	protected JdbcTemplate createJdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	public SimpleJdbcTemplate getSimpleJdbcTemplate() {
		return simpleJdbcTemplate;
	}

	public SearchDialect getSearchDialect() {
		return searchDialect;
	}

	@Autowired
	public void setSearchDialect(SearchDialect searchDialect) {
		this.searchDialect = searchDialect;
	}

}
