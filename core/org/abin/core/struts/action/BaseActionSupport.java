package org.abin.core.struts.action;

import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author ZhangBin
 * 
 */
public class BaseActionSupport extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Map<String,String> errorStack;

	private String infoMessage;

	private String errorMessage;
	
	public Map<String, String> getErrorStack() {
		return errorStack;
	}
	
	public void setErrorStack(Map<String, String> errorStack) {
		this.errorStack = errorStack;
	}
	
	public String getInfoMessage() {
		return infoMessage;
	}
	
	public void setInfoMessage(String infoMessage) {
		this.infoMessage = infoMessage;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
