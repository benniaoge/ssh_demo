package org.bng.core.search;

import java.io.Serializable;

/**
 * 
 * @author ZhangBin
 *
 */
public class SearchContext implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String key;

	private String operation;

	private String context;

	private String target;

	private String parameterKey;

	private boolean object;
	
	public SearchContext(String key) {
		this.key = key;
		this.operation = generateOperation();
		this.context = generateContext();
		this.target = generateTarget();
		this.parameterKey = generateParameterKey();
	}
	
	private String generateOperation() {
		String operation = null;
		if (key.indexOf(".") != -1) {
			operation = key.substring(0, key.indexOf(".")).toLowerCase();
			if(!SearchUtils.isOperationValid(operation)) {
				operation = null;
			}
		}
		return operation;
	}
	
	private String generateContext() {
		String context = null;
		int firstPointPosition = key.indexOf(".");
		int secondPointPosition = key.indexOf(".", firstPointPosition + 1);
		if (firstPointPosition == -1)
			return context;
		else if (firstPointPosition != -1 && secondPointPosition == -1) {
			if (key.endsWith("$")) {
				context = key.substring(firstPointPosition + 1, key.length() - 1);
			} else {
				context = key.substring(firstPointPosition + 1);
			}
		} else {
			context = key.substring(firstPointPosition + 1, secondPointPosition);
		}
		return context;
	}
	
	private String generateTarget() {
		if (key.endsWith("$")) {
			key = key.substring(0, key.length() - 1);
			this.object = true;
		}
		if (key.indexOf(".") == -1)
			return null;
		return key.substring(key.indexOf(".") + 1);
	}
	
	private String generateParameterKey() {
		if (this.operation != null && !this.isOrderSentence()) {
			StringBuffer sb = new StringBuffer(this.operation);
			String[] temp = this.target.split("\\.");
			for (int i = 0; i < temp.length; i++) {
				sb.append(temp[i].substring(0, 1).toUpperCase() + temp[i].substring(1));
			}
			return sb.toString();
		}
		return null;
	}
	
	public boolean validate() {
		return this.operation != null && SearchUtils.isOperationValid(this.operation);
	}
	
	public boolean isOrderSentence() {
		return SearchUtils.isOrderSentence(getOperation());
	}
	
	public boolean isGroupSentence() {
		return SearchUtils.isGroupSentence(getOperation());
	}
	
	public String getSentence(Object parameter) {
		if (this.validate()) {
			return SearchUtils.getSentence(this, parameter);
		}
		return null;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getParameterKey() {
		return parameterKey;
	}

	public void setParameterKey(String parameterKey) {
		this.parameterKey = parameterKey;
	}

	public boolean isObject() {
		return object;
	}

	public void setObject(boolean object) {
		this.object = object;
	}
	

}
