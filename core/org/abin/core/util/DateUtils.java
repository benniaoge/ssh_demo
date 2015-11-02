/**
 * 
 */
package org.abin.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
	/**
	 * 格式化指定日期
	 * @param date
	 * @return yyyy-MM-dd
	 */
	public static String format(Date date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date);
	}
	
	/**
	 * 以指定格式返回日期
	 * @param date
	 * @param df
	 * @return
	 */
	public static String format(Date date, DateFormat df) {
		return df.format(date);
	}
	
	/**
	 * 以指定格式返回日期
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(Date date, String pattern ) {
		DateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}
	
	/**
	 * 返回当前时间
	 * @return
	 */
	public static Date getDate() {
		return Calendar.getInstance().getTime();
	}
	
	/**
	 * 返回当前日历
	 * @return
	 */
	public static Calendar getCalendar() {
		return Calendar.getInstance();
	}

	/**
	 * 返回以毫秒表示的当前系统时间
	 * @return
	 */
	public static String getTimeMillis() {
		return String.valueOf(System.currentTimeMillis());
	}

	/**
	 * 返回当前年份
	 * @return
	 */
	public static int getYear() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.YEAR);
	}
	
	/**
	 * 返回指定日期的年份
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}

	/**
	 * 返回当前年份
	 * @return
	 */
	public static String getYearForStr() {
		int year = getYear();
		return String.valueOf(year);
	}
	
	/**
	 * 返回指定日期的年份
	 * @param date
	 * @return
	 */
	public static String getYearForStr(Date date) {
		int year = getYear(date);
		return String.valueOf(year);
	}

	/**
	 * 以指定长度返回当前年份
	 * @param digit 指定位数
	 * @return
	 */
	public static String getYearForStr(int digit) {
		String year = getYearForStr();
		int length = year.length();
		if (length < digit) {
			return year;
		} else {
			return year.substring(length - digit);
		}
	}
	
	public static Date parseDate(String dateString, String[] patterns) {
		try {
			return org.apache.commons.lang.time.DateUtils.parseDate(dateString, patterns);
		} catch (ParseException e) {
			return null;
		}
	}

}
