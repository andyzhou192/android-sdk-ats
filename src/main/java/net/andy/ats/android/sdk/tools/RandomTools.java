package net.andy.ats.android.sdk.tools;

import java.util.Random;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 
 * @author sifuma@163.com
 *
 */
public class RandomTools {
	
	private static Random rand = new Random();

	public static String randStr(int length) {
		String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int num = rand.nextInt(62);
			buf.append(str.charAt(num));
		}
		return buf.toString();
	}

	public static int randInt(int min, int max) {
		return rand.nextInt(max - min + 1) + min;
	}
	
	public static String randIp() {
		return StringTools.joinStr(".", String.valueOf(randInt(1, 223)),
				String.valueOf(randInt(1, 254)),
				String.valueOf(randInt(1, 254)),
				String.valueOf(randInt(0, 255)));
	}

	public static String randMac() {
		byte[] macAddr = new byte[6];

		rand.nextBytes(macAddr);
		macAddr[0] = (byte) (macAddr[0] & (byte) 254);

		StringBuilder sb = new StringBuilder(18);
		for (byte b : macAddr) {
			if (sb.length() > 0) {
				sb.append(":");
			}
			sb.append(String.format("%02x", b));
		}

		return sb.toString();
	}

	public static String genUniqeID() {
		return DigestUtils.shaHex(String.valueOf(System.currentTimeMillis())
				+ randStr(randInt(16, 32)));
	}

}
