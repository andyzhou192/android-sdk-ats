package net.andy.ats.android.sdk.tools;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;

import com.cmcc.ats.android.sdk.Constants;

/**
 * 
 * @author sifuma@163.com
 *
 */
public class StringTools {

	/**
	 * 将指定字符串转换为JSON串
	 * @param jsonStr
	 * @return JSON对象
	 */
	public static JSONObject convertToJson(String jsonStr){
		//contactList = "{\"result\":{\"contact_count\":\"1\",\"contact_list\":[{\"lastModifiedTime\":\"2014-06-30 13:43:40\",\"createTime\":\"2014-06-30 13:43:40\",\"status\":0,\"dataFromFlag\":\"1\",\"groupMap\":[],\"givenName\":\"\u738b\u4fca\u5b87\",\"contactUserId\":\"1031853202\",\"contactId\":\"6612128302\",\"lastContactTime\":null,\"userId\":1031853202,\"name\":\"\u738b\u4fca\u5b87\",\"syncMobileFlag\":\"1\",\"groups\":[],\"mobile\":[\"18701257471\"]}]},\"id\":\"1404194084187\",\"jsonrpc\":\"2.0\"}";
		JSONObject json = null;
		try {
			json = new JSONObject(jsonStr);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * list 2 String
	 * @param list
	 * @param separator
	 * @return
	 */
	public static String list2String(List<?> list, String separator){
		String result = "";
		for(Object obj : list){
			result += (obj.toString() + separator);
		}
		return result.substring(0, result.length()-1);
	}
	
	/**
	 * array 2 String
	 * @param list
	 * @param separator
	 * @return
	 */
	public static String array2String(Object[] array, String separator){
		String result = "";
		for(Object obj : array){
			result += (obj.toString() + separator);
		}
		return result.substring(0, result.length()-1);
	}
	
	/**
	 * 判断指定字符串是否为空（null、空串、长度不大于0 均为空），为空则返回true，否则返回false
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		return (null == str || str.length() < 1) ? true : false;
	}
	
	/**
	 * 判断指定字符串是否为null，否则返回false
	 * @param str
	 * @return
	 */
	public static String isNullStr(String str){
		return "null".equalsIgnoreCase(str) ? null : str;
	}
	
	/**
	 * 判断指定字符串是否为null，为null则返回true，否则返回false
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str){
		return null == str ? true : false;
	}
	
	/**
	 * 字符串按指定的分隔符组装
	 * @param join  分隔符
	 * @param strings 待组装字符串数组
	 * @return
	 */
	public static String joinStr(String joinSymbol, String... strings) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < strings.length; ++i) {
			if (i == strings.length - 1) {
				sb.append(strings[i]);
			} else {
				sb.append(strings[i]).append(joinSymbol);
			}
		}
		return sb.toString();
	}

	/**
	 * 字符串按指定的分隔符组装
	 * @param join 分隔符
	 * @param params 待组装字符串map
	 * @return
	 */
	public static String joinStr(String joinSymbol, Map<String, String> params) {
		return joinStr(joinSymbol, params, true);
	}

	/**
	 * 字符串按指定的分隔符组装
	 * @param join  分隔符
	 * @param params 待组装字符串map
	 * @param urlEncode 是否采用utf-8编码
	 * @return
	 */
	public static String joinStr(String join, Map<String, String> params,
			boolean urlEncode) {
		StringBuilder sb = new StringBuilder();

		for (Entry<String, String> entry : params.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();

			if (value == null) {
				continue;
			}

			if (sb.length() > 0) {
				sb.append(join);
			}

			try {
				if (urlEncode) {
					sb.append(String.format("%s=%s", key,
							URLEncoder.encode(value, Constants.ENCODING)));
				} else {
					sb.append(String.format("%s=%s", key, value));
				}
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e.getCause());
			}
		}

		return sb.toString();
	}
	
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//
//	}

}
