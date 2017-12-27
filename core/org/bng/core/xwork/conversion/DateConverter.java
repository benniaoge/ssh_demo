package org.bng.core.xwork.conversion;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import ognl.DefaultTypeConverter;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.bng.core.util.DateUtils;

public class DateConverter extends DefaultTypeConverter {

	private static final Logger logger = Logger.getLogger(DateConverter.class);

	private static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	private static final String DATE_PATTERN = "yyyy-MM-dd";

	/**
	 * Convert value between types
	 */
	@SuppressWarnings("rawtypes")
	public Object convertValue(Map ognlContext, Object value, Class toType) {
		Object result = null;
		if (toType == Date.class) {
			result = doConvertToDate(value);
		} else if (toType == String.class) {
			result = doConvertToString(value);
		}
		return result;
	}

	private Date doConvertToDate(Object value) {
		Date result = null;

		if (value instanceof String) {
			// TODO add date converter parse order here
			result = DateUtils.parseDate((String) value, new String[] { DATETIME_PATTERN, DATE_PATTERN });

			// all patterns failed, try a milliseconds constructor
			if (result == null && StringUtils.isNotEmpty((String) value)) {

				try {
					result = new Date(new Long((String) value).longValue());
				} catch (Exception e) {
					logger.error("Converting from milliseconds to Date fails!");
					e.printStackTrace();
				}

			}

		} else if (value instanceof Object[]) {
			// let's try to convert the first element only
			Object[] array = (Object[]) value;

			if ((array != null) && (array.length >= 1)) {
				value = array[0];
				result = doConvertToDate(value);
			}

		} else if (Date.class.isAssignableFrom(value.getClass())) {
			result = (Date) value;
		}
		return result;
	}

	private String doConvertToString(Object value) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATETIME_PATTERN);
		String result = null;
		if (value instanceof Date) {
			result = simpleDateFormat.format(value);
		}
		return result;
	}

}
