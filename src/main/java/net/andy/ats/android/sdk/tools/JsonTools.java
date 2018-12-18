package net.andy.ats.android.sdk.tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.andy.ats.android.sdk.util.LogUtils;

public class JsonTools {
	
	/**
	 * 获取JSON字符串中指定的JSON对象
	 * 注意：本方法只是针对复杂结构的JSON
	 * @param jsonStr
	 * @param key
	 * @return
	 */
	public static JSONObject getJSONObject(String jsonStr, String...keys){
		return (JSONObject) getFiled(jsonStr, 2, keys);
	}
	
	/**
	 * 获取JSON字符串中指定的JSON对象数组
	 * 注意：本方法只是针对复杂结构的JSON
	 * @param jsonStr
	 * @param key
	 * @return
	 */
	public static JSONArray getJSONArray(String jsonStr, String...keys){
		return (JSONArray) JsonTools.getFiled(jsonStr, 3, keys);
	}

	/**
	 * 获取JSON字符串中指定的字段值
	 * 注意：本方法只是针对简单单层结构的JSON,当keys为空时，返回由字符串转换的到的JSON对象
	 * @param jsonStr: json字符串，必须为JSON格式
	 * @param flag: 返回结果类型【
	 * 			0-将传入的JSON字符串转换成JSON对象，
	 * 			1-返回JSON字符串中指定的字段值，
	 * 			2-返回JSON字符串中指定的JSON对象，
	 * 			3-返回JSON字符串中指定的JSON对象数组】
	 * @param keys
	 * @return
	 */
	public static Object getFiled(String jsonStr, int flag, String...keys){
		JSONObject obj = StringTools.convertToJson(jsonStr);
		int count = keys.length;
		try {
			switch (flag){
			case 0:
				if(count == 0){
					return obj;
				}else{
					LogUtils.err(JsonTools.class, "参数传入错误，当flag=0时，keys应该为空，即：不传该参数.");
				}
				break;
				
			case 1:
				if(count == 1){
					return obj.get(keys[0]);
				}else if(count>1){
					for(int i=0; i<count-1; i++){
						obj = obj.getJSONObject(keys[i]);
					}
					return obj.get(keys[count-1]);
				}else{
					LogUtils.err(JsonTools.class, "参数传入错误，当flag=1时，keys应该不为空，至少有一个值.");
				}
				break;
				
			case 2:
				if(count == 1){
					return obj.getJSONObject(keys[0]);
				}else if(count>1){
					for(int i=0; i<count; i++){
						obj = obj.getJSONObject(keys[i]);
					}
					return obj;
				}else{
					LogUtils.err(JsonTools.class, "参数传入错误，当flag=2时，keys应该不为空，至少有一个值.");
				}
				break;
				
			case 3:
				if(count == 1){
					return obj.getJSONArray(keys[0]);
				}else if(count>1){
					for(int i=0; i<count-1; i++){
						obj = obj.getJSONObject(keys[i]);
					}
					return obj.getJSONArray(keys[count-1]);
				}else{
					LogUtils.err(JsonTools.class, "参数传入错误，当flag=2时，keys应该不为空，至少有一个值.");
				}
				break;
				
			default:
				LogUtils.err(JsonTools.class, "flag参数传入错误.");
			}
					
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}
}
