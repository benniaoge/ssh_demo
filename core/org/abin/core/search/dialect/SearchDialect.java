/**
 * 
 */
package org.abin.core.search.dialect;

/**
 * @author ZhangBin
 *
 */
public interface SearchDialect {

	public String getSearchLimitSql(String sql, int firstResult, int maxResults, boolean isPutValue);
	
}
