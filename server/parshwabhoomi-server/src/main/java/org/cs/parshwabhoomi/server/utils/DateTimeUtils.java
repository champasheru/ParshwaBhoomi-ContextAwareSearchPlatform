/**
 * parshwabhoomi-server	19-Dec-2017:7:45:03 PM
 */
package org.cs.parshwabhoomi.server.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;

/**
 * @author gayatri
 * git: champasheru dange.gayatri@gmail.com
 *
 */
public class DateTimeUtils {
	private static SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	
	public static Date getDateFromString(String source){
		Date date = null;
		try {
			date = format.parse(source);
		} catch (ParseException e) {
			LogManager.getLogger().error("Couldn't parse date:"+date, e);
		}
		return date;
	}
	
	
	public static String getFormattedDate(Date date){
		return format.format(date);
	}
}
