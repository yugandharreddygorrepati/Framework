/**
 * 
 */
package com.framework.lib;
/**
 * @author YugandharReddyGorrep
 *
 */
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
public class DateUtils
{
	static Locale locale = Locale.getDefault();
	static TimeZone tz = TimeZone.getDefault();
	static Calendar cal = Calendar.getInstance(tz, locale);
	static Date d = new Date(System.currentTimeMillis());
	/**
	 * @description:Returns the current time stamp
	 * 
	 * 
	 * @return String
	 */
	public static String getCurrTimeStamp()
	{
		LocalDate.now();
		Locale locale = Locale.getDefault();
		TimeZone tz = TimeZone.getDefault();
		Calendar cal = Calendar.getInstance(tz, locale);
		Date d = new Date(System.currentTimeMillis());
		cal.setTime(d);
		int m = cal.get(Calendar.MONTH) + 1;
		int h = cal.get(Calendar.HOUR);
		int mm = cal.get(Calendar.MINUTE);
		int s = cal.get(Calendar.SECOND);
		String timeStamp = cal.get(Calendar.DAY_OF_MONTH) + "_" + m + "_" + cal.get(Calendar.YEAR) + "_" + h + "hh_"
				+ mm + "mm_" + s + "ss";
		return timeStamp;
	}
	public static String getCurrMonthInMM()
	{
		cal.setTime(d);
		int m = cal.get(Calendar.MONTH) + 1;
		String mm;
		if (m < 10)
			mm = "0" + m;
		else
			mm = Integer.toString(m);
		return mm;
	}
	public static String getCurrDateInDD()
	{
		int d = cal.get(Calendar.DATE);
		String mm;
		if (d < 10)
			mm = "0" + d;
		else
			mm = Integer.toString(d);
		return mm;
	}
	public static Integer getCurrYearInYYYY()
	{
		int y = cal.get(Calendar.YEAR);
		return y;
	}
}
