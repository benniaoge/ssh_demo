package org.bng.core.struts.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

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
	
	private HttpServletRequest request = ServletActionContext.getRequest();
	
	private HttpServletResponse response = ServletActionContext.getResponse();
	
	private Map<String,String> errorStack;

	private String infoMessage;

	private String errorMessage;
	
	public HttpServletRequest getRequest() {
		return request;
	}
	
	public HttpServletResponse getResponse() {
		return response;
	}
	
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
