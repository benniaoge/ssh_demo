/**
 * 
 */
package org.abin.core.search;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.abin.core.search.dialect.SearchDialect;
import org.apache.commons.lang.StringUtils;


/**
 * @author ZhangBin
 *
 */
public class SearchUtils {
	
	public static List<String> COMPARE_OPERATORS = Arrays.asList(new String[] { "eq", "neq", "gt", "lt", "geq", "leq", "like", "in" });

	public static List<String> ORDER_OPERATORS = Arrays.asList(new String[] { "orderby" });

	public static List<String> GROUP_OPERATORS = Arrays.asList(new String[] { "groupby" });

	public static Map<String,String> OPERATION_MAPPING = new HashMap<String,String>();

	static {
		OPERATION_MAPPING.put("eq", " = ");
		OPERATION_MAPPING.put("neq", " <> ");
		OPERATION_MAPPING.put("gt", " > ");
		OPERATION_MAPPING.put("lt", " < ");
		OPERATION_MAPPING.put("geq", " >= ");
		OPERATION_MAPPING.put("leq", " <= ");
		OPERATION_MAPPING.put("like", " LIKE ");
		OPERATION_MAPPING.put("in", " IN ");
		OPERATION_MAPPING.put("orderby", " ORDER BY ");
		OPERATION_MAPPING.put("groupby", " GROUP BY ");
	}
	
	public static boolean isWhereSentence(String operation) {
		return COMPARE_OPERATORS.contains(operation);
	}
	
	public static boolean isOrderSentence(String operation) {
		return ORDER_OPERATORS.contains(operation);
	}

	public static boolean isGroupSentence(String operation) {
		return GROUP_OPERATORS.contains(operation);
	}
	
	public static boolean isOperationValid(String operation) {
		return isWhereSentence(operation) || isOrderSentence(operation) || isGroupSentence(operation);
	}
	
	public static String getSentence(SearchContext searchContext, Object value) {
		StringBuffer sb = new StringBuffer();
		if (searchContext.isOrderSentence()) {
			sb.append(searchContext.getTarget());
			sb.append(" ").append(value.toString());
		} else if (searchContext.isGroupSentence()) {
			sb.append(searchContext.getTarget());
		} else {
			sb.append(" AND (");
			sb.append(searchContext.getTarget());
			sb.append(OPERATION_MAPPING.get(searchContext.getOperation()));
			sb.append(":").append(searchContext.getParameterKey());
			sb.append(")");
		}
		return sb.toString();
	}
	
	public static String getLimitedSentence(SearchDialect dialect, String sql, int firstResult, int maxResults, boolean isPutValue) {
		return dialect.getSearchLimitSql(sql, firstResult, maxResults, isPutValue);
	}
	
	public static String getHQLCountSentence(String hql) {
		StringBuffer sb = new StringBuffer("SELECT count(*) ");
		String tempSql = StringUtils.trim(hql).toUpperCase();
		if (tempSql.startsWith("FROM")) {
			return sb.append(hql).toString();
		} else {
			int index = tempSql.indexOf(" FROM ");
			return sb.append(hql.substring(index)).toString();
		}
	}
	
	public static String getSQLCountSentence(String sentence) {
		StringBuffer sb = new StringBuffer("SELECT count(*) FROM (");
		return sb.append(sentence).append(") countTemp_").toString();
	}
	
	public static String getCountSentence(Class<?> entityClass) {
		String querySql = getQuerySentence(entityClass);
		return getHQLCountSentence(querySql);
	}
	
	public static String getQuerySentence(Class<?> entityClass) {
		String entityClassName = getEntityClassName(entityClass);
		StringBuffer querySql = new StringBuffer();
		querySql.append("FROM ").append(entityClassName);
		querySql.append(" ").append(getAlias(entityClass)).append(" ");
		return querySql.toString();
	}
	
	public static String getAlias(Class<?> entityClass) {
		String entityClassName = getEntityClassName(entityClass);
		return entityClassName.substring(0, 1).toLowerCase() + entityClassName.substring(1);
	}
	
	public static String getEntityClassName(Class<?> entityClass) {
		return entityClass.getName().substring(entityClass.getName().lastIndexOf(".") + 1);
	}

}
