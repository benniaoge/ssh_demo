package org.bng.core.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

public class BeanUtils {

	/**
	 * 
	 * @param clazz
	 * @return
	 */
	public static <T> T reflectClass(Class<T> clazz) {
		try {
			return clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param dest
	 * @param source
	 */
	public static void copyProperties(Object dest, Object source) {
		try {
			PropertyUtils.copyProperties(dest, source);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param bean
	 * @param properties
	 */
	public static void populate(Object bean, Map<String,?> properties) {
		try {
			org.apache.commons.beanutils.BeanUtils.populate(bean, properties);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param dest
	 * @param source
	 */
	public static void copyBean(Object dest, Object source) {
		try {
			org.apache.commons.beanutils.BeanUtils.copyProperties(dest, source);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/**
	 * check id of bean
	 * 
	 * @param bean
	 * @param property
	 * @return
	 */
	public static boolean isPropertyNotEmpty(Object bean, String property) {
		return isPropertyNotEmpty(bean, property, "id");
	}

	/**
	 * check specified nested property of bean
	 * 
	 * @param bean
	 * @param property
	 * @return
	 */
	public static boolean isPropertyNotEmpty(Object bean, String property, String nestedCheckProperty) {
		if (bean == null)
			return false;

		try {
			Object target = PropertyUtils.getProperty(bean, property);
			if (target != null) {
				Object id = PropertyUtils.getProperty(target, nestedCheckProperty);
				if (id != null && id instanceof String && org.apache.commons.lang.StringUtils.isNotBlank((String) id)) {
					return true;
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Check if array is null or empty
	 * 
	 * @param array
	 * @return
	 */
	public static boolean isArrayEmpty(String[] array) {
		boolean result = true;
		if (array != null) {
			for (int i = 0; i < array.length; i++) {
				if (org.apache.commons.lang.StringUtils.isNotBlank(array[i]))
					return false;
			}
		}
		return result;
	}

	/**
	 * Check if array has single value
	 * 
	 * @param array
	 * @return
	 */
	public static boolean isArraySingle(String[] array) {
		if (array == null)
			return false;
		return array.length == 1;
	}
	
	/**
	 * 
	 * @param beans
	 * @param property
	 * @return
	 */
	public static List<?> getPropertyList(List<?> beans, String property) {
		List<Object> result = new ArrayList<Object>();
		for(int i = 0; i < beans.size(); i++) {
			try {
				result.add(PropertyUtils.getProperty(beans.get(i), property));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
}
