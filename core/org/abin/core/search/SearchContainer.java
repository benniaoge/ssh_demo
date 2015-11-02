package org.abin.core.search;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SearchContainer implements Serializable {

	private static final long serialVersionUID = 1L;

	private Map<String, Object> map = new HashMap<String, Object>();
	
	public Map<String, Object> getContext() {
		return map;
	}
	
	public Object get(String key) {
		return map.get(key);
	}
	
	public void set(String key, Class<?> persistentClass) {
		try {
			map.put(key, persistentClass.newInstance());
		} catch (Exception e) {
			map.put(key, null);
		}
	}
	
	public boolean contains(String key) {
		return this.map.containsKey(key);
	}
	
	public boolean isEmpty() {
		return this.map.isEmpty();
	}

}
