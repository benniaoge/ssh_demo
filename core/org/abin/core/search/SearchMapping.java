package org.abin.core.search;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class SearchMapping implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static Map<String,Class<?>> persistentClassCache = new HashMap<String,Class<?>>();
	
	public static Class<?> getPersistentClass(String alias) {
		return (Class<?>) persistentClassCache.get(alias);
	}
	
	public static void init(Set<String> keySet) {
		for(Iterator<String> iter = keySet.iterator(); iter.hasNext();) {
			try {
				Class<?> persistentClass = Class.forName(iter.next());
				persistentClassCache.put(SearchUtils.getAlias(persistentClass), persistentClass);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setConfigLocation(String filePath) {
		ResourceBundle bundle = ResourceBundle.getBundle(filePath);
		
		for (Enumeration<String> keys = bundle.getKeys(); keys.hasMoreElements();) {
			String key = keys.nextElement();
			Class<?> clazz = null;
			try {
				clazz = Class.forName(bundle.getString(key));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			if (clazz != null) {
				persistentClassCache.put(key, clazz);
			}
		}
	}

}
