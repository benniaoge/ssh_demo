/**
 * 
 */
package org.bng.core.util;

import java.util.Map;


/**
 * @author ZhangBin
 *
 */
public class StringUtils {

	public static String replacePlaceHolder(String template, Map<String,String> valueMap) {

		if (template.indexOf("${") == -1) {
			return template;
		}

		while (true) {
			int start = template.indexOf("${");
			int end = template.indexOf("}", start);

			if (start != -1 && end != -1) {
				String temp = template.substring(start + 2, end);

				if (valueMap.keySet().contains(temp)) {
					template = template.substring(0, start) + valueMap.get(temp) + template.substring(end + 1);
				} else {
					template = template.substring(0, start) + template.substring(end + 1);
				}

			} else {
				break;
			}
		}

		return template;
	}

	public static boolean hasPlaceHolder(String template) {
		if (template.indexOf("${") == -1) {
			return false;
		} else {
			return true;
		}
	}

	public static String trim(String string) {
		return org.apache.commons.lang.StringUtils.trim(string);
	}

}
