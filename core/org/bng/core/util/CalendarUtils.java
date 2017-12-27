/**
 * 
 */
package org.bng.core.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @author ZhangBin
 * 
 */
public class CalendarUtils {
	
	public static Date addDays(Integer days) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, days);
		return c.getTime();
	}

}
