package com.abin.demo.dict.tag;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;

public class DictTag extends BodyTagSupport {

	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(DictTag.class);

	private String type;

	private String code;

	@Override
	public int doStartTag() throws JspException {
		
		if(validateType()) {
			@SuppressWarnings("unchecked")
			Map<Integer, Map<String, String>> dictMaps = (Map<Integer, Map<String, String>>) pageContext.getServletContext().getAttribute("dictMaps");
			Map<String,String> dictMap = dictMaps.get(Integer.valueOf(type));

			if(dictMap != null) {
				String codeName = dictMap.get(code);
				outputDictPart(codeName);
			}
		}

		return EVAL_BODY_INCLUDE;
	}
	
	private void outputDictPart(String codeName) throws JspException {
		JspWriter writer = pageContext.getOut();
		try {
			writer.write(codeName);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		
		if(StringUtils.isBlank(code)) {
			flag = false;
			if (log.isDebugEnabled())
				log.debug("---------- code is null -------------");
		}

		return flag;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
