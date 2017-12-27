package org.bng.core.util;

import java.util.ResourceBundle;

public class ConfigUtils {
	
	private static ResourceBundle rb =	ResourceBundle.getBundle("config");
	
	public static String getString(String key) {
		return rb.getString(key);
	}

}
 