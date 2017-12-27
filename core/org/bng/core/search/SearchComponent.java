package org.bng.core.search;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.bng.core.util.StringUtils;


/**
 * 
 * @author ZhangBin
 *
 */
public class SearchComponent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Map<String, String> whereSentences = new HashMap<String, String>();

	private Map<String, String> orderSentences = new HashMap<String, String>();

	private Map<String, String> groupSentences = new HashMap<String, String>();

	private Map<String, Object> orderParameters = new HashMap<String, Object>();
	
	private Map<String, Object> parameters = new HashMap<String, Object>();
	
	public SearchComponent() {
	}

	public SearchComponent(final Map<String, Object> searchElements) {
		addSearchElements(searchElements);
	}
	
	public SearchComponent addSearchElements(final Map<String, Object> searchElements) {
		for (Iterator<Map.Entry<String, Object>> iter = searchElements.entrySet().iterator(); iter.hasNext();) {
			Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iter.next();
			this.addSearchElement(entry.getKey(), entry.getValue());
		}
		return this;
	}
	
	public SearchComponent addSearchElement(String key, Object parameter) {
		SearchContext searchContext = new SearchContext(key);

		if (searchContext.isGroupSentence()) {
			this.addGroupSentence(searchContext, parameter);
		} else {

			if (parameter != null) {

				if (searchContext.isOrderSentence()) {
					this.addOrderSentence(searchContext, parameter);
					this.addOrderParameter(searchContext.getTarget(), parameter);
				} else {
					this.addWhereSentence(searchContext, parameter);
					this.addParameter(searchContext.getParameterKey(), parameter);
				}

			} else {
				
				if(whereSentences.containsKey(key)) {
					this.whereSentences.remove(key);
					this.parameters.remove(searchContext.getParameterKey());
				}
				
				if(orderSentences.containsKey(key)) {
					this.orderSentences.remove(key);
					this.orderParameters.remove(searchContext.getTarget());
				}
			}
		}
		return this;
	}
	
	public String getHQLCountSentence(String sentenceTemplate, boolean dynamic) {
		String template = StringUtils.trim(sentenceTemplate);
		template = StringUtils.replacePlaceHolder(template, getSentenceMap());
		if (dynamic && !StringUtils.hasPlaceHolder(template)) {
			template += generateWhereSentence(template);
		}
		template += generateGroupSentence(template);
		return SearchUtils.getHQLCountSentence(template);
	}
	
	public String getSQLCountSentence(String sentenceTemplate, boolean dynamic) {
		String template = StringUtils.trim(sentenceTemplate);
		template = StringUtils.replacePlaceHolder(template, getSentenceMap());
		if (dynamic && !StringUtils.hasPlaceHolder(template)) {
			template += generateWhereSentence(template);
		}
		template += generateGroupSentence(template);
		return SearchUtils.getSQLCountSentence(template);
	}
	
	public String getSearchSentence(String sentenceTemplate, boolean dynamic) {
		String template = StringUtils.trim(sentenceTemplate);
		template = StringUtils.replacePlaceHolder(template, getSentenceMap());
		if (dynamic && !StringUtils.hasPlaceHolder(template)) {
			template += generateWhereSentence(template);
		}
		template += generateGroupSentence(template);
		template += generateOrderSentence(template);
		return template;
	}
	
	public Map<String, Object> getReturnedParameters() {
		Map<String, Object> params = new HashMap<String, Object>();
		for(Iterator<Map.Entry<String, Object>> iter = parameters.entrySet().iterator(); iter.hasNext();) {
			Map.Entry<String, Object> entry = iter.next();
			if(entry.getKey().startsWith("like")) {
				String value = (String) entry.getValue();
				params.put(entry.getKey(), value.substring(1, value.length() - 1));
			} else {
				params.put(entry.getKey(), entry.getValue());
			}
		}
		return params;
	}
	
	public Map<String, Object> getAliasOrderParameters() {
		Map<String, Object> aliasOrderParameters = new HashMap<String, Object>();
		if(this.getOrderParameters() != null) {
			for(Iterator<Map.Entry<String, Object>> iter = this.getOrderParameters().entrySet().iterator(); iter.hasNext();) {
				Map.Entry<String, Object> entry = iter.next();
				if(entry.getKey().indexOf(".") != -1) {
					aliasOrderParameters.put(entry.getKey().substring(0, entry.getKey().indexOf(".")), entry.getValue());
				}
			}
		}
		return aliasOrderParameters;
	}

	private String generateWhereSentence(String sentenceTemplate) {
		StringBuffer sb = new StringBuffer();
		for (Iterator<String> iter = this.whereSentences.values().iterator(); iter.hasNext();) {
			sb.append(iter.next());
		}
		return sb.toString();
	}

	private String generateOrderSentence(String sentenceTemplate) {
		StringBuffer sb = new StringBuffer();
		if (!this.orderSentences.isEmpty()) {
			sb.append(" ORDER BY ");
		}
		for (Iterator<String> iter = this.orderSentences.values().iterator(); iter.hasNext();) {
			sb.append(iter.next());
			if (iter.hasNext()) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	private String generateGroupSentence(String sentenceTemplate) {
		StringBuffer sb = new StringBuffer();
		if (!this.groupSentences.isEmpty()) {
			sb.append(" GROUP BY ");
		}
		for (Iterator<String> iter = this.groupSentences.values().iterator(); iter.hasNext();) {
			sb.append(iter.next());
			if (iter.hasNext()) {
				sb.append(",");
			}
		}
		return sb.toString();
	}
	
	private void addWhereSentence(SearchContext searchContext, Object parameter) {
		this.whereSentences.put(searchContext.getKey(), searchContext.getSentence(parameter));
	}

	private void addOrderSentence(SearchContext searchContext, Object parameter) {
		this.orderSentences.put(searchContext.getKey(), searchContext.getSentence(parameter));
	}

	private void addGroupSentence(SearchContext searchContext, Object parameter) {
		this.groupSentences.put(searchContext.getKey(), searchContext.getSentence(parameter));
	}

	private void addParameter(String parameterKey, Object parameter) {
		this.parameters.put(parameterKey, parameter);
	}
	
	private void addOrderParameter(String orderParameterKey, Object parameter) {
		this.orderParameters.put(orderParameterKey, parameter);
	}
	
	public void resetOrderSentence() {
		this.orderParameters = new HashMap<String, Object>();
		this.orderSentences = new HashMap<String, String>();
	}
	
	public void resetGroupSentence() {
		this.groupSentences = new HashMap<String, String>();
	}
	
	public void resetWhereSentence() {
		this.whereSentences = new HashMap<String, String>();
		this.parameters = new HashMap<String, Object>();
	}
	
	public Map<String, Object> getOrderParameters() {
		return orderParameters;
	}
	
	public Map<String, Object> getParameters() {
		return parameters;
	}
	
	public Map<String, String> getSentenceMap() {
		Map<String, String> sentenceMap = new HashMap<String, String>();
		sentenceMap.putAll(whereSentences);
		return sentenceMap;
	}

}
