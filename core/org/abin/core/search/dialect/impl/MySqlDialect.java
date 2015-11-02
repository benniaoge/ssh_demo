/**
 * 
 */
package org.abin.core.search.dialect.impl;

import org.abin.core.search.dialect.SearchDialect;

/**
 * @author ZhangBin
 * 
 */
public class MySqlDialect implements SearchDialect {

	@Override
	public String getSearchLimitSql(String sql, int firstResult, int maxResults, boolean isPutValue) {
		StringBuffer sb = new StringBuffer(sql);
		if (isPutValue) {
			sb.append(firstResult > 0 ? " limit " + firstResult + ", " + maxResults : " limit " + maxResults);
		} else {
			sb.append(firstResult > 0 ? " limit :firstResult, :maxResults" : " limit :maxResults");
		}
		return sb.toString();
	}

}
