package com.abin.demo.loader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletContext;

import org.abin.core.loader.ServletContextLoader;
import org.abin.core.service.GenericService;
import org.abin.core.util.WebContextUtils;

import com.abin.demo.dict.entity.Dict;

public class DictLoader implements ServletContextLoader {

	@Override
	public void initServletContext(ServletContext servletContext) {
		@SuppressWarnings("unchecked")
		GenericService<Dict> genericService = WebContextUtils.getBean(servletContext, "genericService", GenericService.class);
		List<Dict> dicts = genericService.loadAll(Dict.class);

		Map<Integer, Map<String, String>> dictMaps = new HashMap<Integer, Map<String, String>>();
		
		for (Dict dict : dicts) {
			Map<String, String> dictMap = dictMaps.get(dict.getType());
			if (dictMap == null) {
				dictMap = new TreeMap<String, String>();
			}
			
			dictMap.put(dict.getCode(), dict.getCodeName());
			dictMaps.put(dict.getType(), dictMap);
		}
		
		servletContext.setAttribute("dictMaps", dictMaps);
	}

	@Override
	public void closeServletContext(ServletContext servletContext) {
		servletContext.removeAttribute("dictMaps");
	}
	

}
