package org.bng.core.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;

import org.bng.core.exception.BusinessException;
import org.springframework.core.io.ClassPathResource;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreemarkerUtils {

	public static String processTemplateFile(Map<String, Object> root, String filePath) {
		StringWriter sw = new StringWriter();
		String result = "";
		
		Configuration cfg = new Configuration();
		cfg.setEncoding(Locale.CHINA, "UTF-8");
		
		try {
			cfg.setDirectoryForTemplateLoading(new ClassPathResource("template").getFile());
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException("FreeMarker template directory error");
		}
		
		try {
			Template ftl = cfg.getTemplate(filePath, Locale.CHINA, "UTF-8");
			try {
				ftl.process(root, sw);
				result = sw.toString();
			} catch (TemplateException e) {
				e.printStackTrace();
				throw new BusinessException("FreeMarker template process failed");
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new BusinessException("FreeMarker template file parse error");
		}finally {
			try {
				sw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
}
