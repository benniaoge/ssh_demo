package com.abin.demo.tag;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.abin.demo.util.Constants;

public class DictTag extends BodyTagSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String type;
	
	private Object key;
	
	@Override
	public int doStartTag() throws JspException {
		if(Constants.DictTag.FILE_STATUS.equals(type)) {
			outputFileStatusPart();	
		}else if(Constants.DictTag.TARGET_STATUS.equals(type)) {
			outputTargetStatusPart();
		}
		return EVAL_BODY_INCLUDE;
	}
	
	@SuppressWarnings("unchecked")
	private void outputFileStatusPart() throws JspException {
		Map<String, String> dictMaps = (Map<String, String>)pageContext.getServletContext().getAttribute(Constants.DictTag.FILE_STATUS);
		
		JspWriter writer = pageContext.getOut();
		
		try {
			writer.write(dictMaps.get(key.toString()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void outputTargetStatusPart() throws JspException {
		Map<String, String> dictMaps = (Map<String, String>)pageContext.getServletContext().getAttribute(Constants.DictTag.TARGET_STATUS);
		
		JspWriter writer = pageContext.getOut();
		
		try {
			writer.write(dictMaps.get(key.toString()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Object getKey() {
		return key;
	}
	
	public void setKey(Object key) {
		this.key = key;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

}
