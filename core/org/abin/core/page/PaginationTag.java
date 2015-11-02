package org.abin.core.page;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.abin.core.util.VelocityUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.taglibs.standard.tag.el.core.ExpressionUtil;

public class PaginationTag extends BodyTagSupport {

	private static final long serialVersionUID = 1L;
	
	/**
	 * class name of CSS Style
	 */
	private String styleClass;
	
	/**
	 * the condition need to met to render the paginatino segament
	 */
	private String test;
	
	/**
	 * the url to submit
	 */
	private String action;
	
	@Override
	public int doStartTag() throws JspException {
		if(StringUtils.isNotBlank(getTest())) {
			if((Boolean)ExpressionUtil.evalNotNull("page", "test", test, Boolean.class, this, pageContext)){
				outputPaginationPart();
			}
		}else {
			outputPaginationPart();
		}
		return EVAL_BODY_INCLUDE;
	}
	
	private void outputPaginationPart() throws JspException {
		Page page = (Page)pageContext.getRequest().getAttribute("page");
		JspWriter writer = pageContext.getOut();
		try {
			String result = processPaginationTemplate(page);
			writer.write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String processPaginationTemplate(Page page) throws JspException {
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("styleClass", this.getStyleClass());
		root.put("action", ExpressionUtil.evalNotNull("page","action", this.getAction(), String.class, this, pageContext));
		root.put("prePage", page.hasPrePage());
		root.put("nextPage", page.hasNextPage());
		root.put("totalPage", page.getTotalPage());
		root.put("currentPage", page.getCurrentPage());
		root.put("formid", new Date().getTime());
//		return FreemarkerUtils.processTemplateFile(root, "pagination.ftl");
		return VelocityUtils.processTemplateFile(root, "pagination.vm");
	}

	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
}
