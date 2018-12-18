package net.andy.ats.android.sdk.tools;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author sifuma@163.com
 *
 */
public class TimeTools {

	public static String formatTime(String format) {
		return new SimpleDateFormat(format).format(new Date());
	}

	public static String formatTime(long time, String format) {
		return new SimpleDateFormat(format).format(new Timestamp(time));
	}
	
	public static Date formatTime(String time, String format) {
		try {
			return new SimpleDateFormat(format).parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
