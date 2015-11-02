package com.abin.demo.dict.tag;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

public class SelectTag extends BodyTagSupport {

	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(SelectTag.class);

	private String type;
	
	private String selectValue;

	@Override
	public int doStartTag() throws JspException {
		
		if(validateType()) {
			@SuppressWarnings("unchecked")
			Map<Integer, Map<String, String>> dictMaps = (Map<Integer, Map<String, String>>) pageContext.getServletContext().getAttribute("dictMaps");
			Map<String,String> dictMap = dictMaps.get(Integer.valueOf(type));

			if(dictMap != null) {
				outputSelectPart(dictMap);
			}
		}

		return EVAL_BODY_INCLUDE;
	}
	
	private void outputSelectPart(Map<String,String> dictMap) throws JspException {
		JspWriter writer = pageContext.getOut();
		try {
			writer.write(generateOptions(dictMap));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String generateOptions(Map<String,String> dictMap) {
		StringBuffer options = new StringBuffer();
		options.append("<option value=''>");
		options.append("请选择...");
		options.append("</option>");

		for(Map.Entry<String, String> entry : dictMap.entrySet()) {
			options.append("<option value=\"");
			options.append(entry.getKey());
			options.append("\"");
			if(StringUtils.isNotBlank(selectValue) && selectValue.equals(entry.getKey())) {
				options.append(" selected='selected'");
			}
			options.append(">");
			options.append(entry.getValue());
			options.append("</option>");
		}
		
		return options.toString();
	}

	private boolean validateType() {
		boolean flag = true;

		if (StringUtils.isBlank(type)) {
			flag = false;
			if (log.isDebugEnabled())
				log.debug("---------- type is null -------------");
		}

		if (!NumberUtils.isNumber(type)) {
			flag = false;
			if (log.isDebugEnabled())
				log.debug("---------- type is not number -------------");
		}
		
		return flag;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getSelectValue() {
		return selectValue;
	}
	
	public void setSelectValue(String selectValue) {
		this.selectValue = selectValue;
	}

}
