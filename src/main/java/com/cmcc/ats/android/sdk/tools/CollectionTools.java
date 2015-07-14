package com.cmcc.ats.android.sdk.tools;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.cmcc.ats.android.sdk.Constants;

public class CollectionTools {

	/**
	 * 将list转换成数组
	 * @param list
	 * @return
	 */
	public static String[] listToStrArray(List<?> list){
		if(null == list || list.size()<1){
			return new String[0];
		}
		String[] array = new String[list.size()];
		for(int i = 0; i < list.size(); i++){
			array[i] = list.get(i).toString();
		}
		return array;
	}
	
	/**
	 * 获取map的所有key
	 * @param map
	 * @return
	 */
	public static String[] getMapKeys(Map<String, ?> map){
		if(null == map){
			return null;
		}
		String[] array = new String[map.size()];
		Iterator<String> it = map.keySet().iterator();
		int index = 0;
		while (it.hasNext()){
			array[index] = it.next();
			index++;
		}
		return array;
	}
	
	/**
	 * 字符串转换成map，字符串格式（key1:value1,key2:value2）
	 * @param map
	 * @return
	 */
	public static Map<String, String> convertToMap(String str){
		Map<String, String> map = new HashMap<String, String>();
		if(null == str){
			return null;
		}
		for(String kv : str.split(Constants.COMMA)){
			map.put(kv.split(Constants.COLON)[0], kv.split(Constants.COLON)[1]);
		}
		return map;
	}
}
