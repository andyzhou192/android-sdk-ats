package net.andy.ats.android.sdk.util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * 
 * @author sifuma@163.com
 *
 */
public class LogUtils {
	
	public static void info(Class<?> cl, String msg){
		Logger LOGGER = LogManager.getLogger(cl);
		LOGGER.info(msg);
	}
	
	public static void debug(Class<?> cl, String msg){
		Logger LOGGER = LogManager.getLogger(cl);
		LOGGER.debug(msg);
	}
	
	public static void err(Class<?> cl, String msg){
		Logger LOGGER = LogManager.getLogger(cl);
		LOGGER.error(msg);
	}
	
	public static void warn(Class<?> cl, String msg){
		Logger LOGGER = LogManager.getLogger(cl);
		LOGGER.warn(msg);
	}

}
