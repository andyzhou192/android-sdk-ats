package com.cmcc.ats.android.sdk.tools;

/**
 * 
 * @author zhouyelin@chinamobile.com
 *
 */
public class ThreadTools {

	public static void sleepInSeconds(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
