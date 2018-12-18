package net.andy.ats.android.sdk.util;

import java.util.List;
import java.util.Map;

public interface Excel2003Handler {
	
	/**
	 * ��ȡexcel�г���һ��֮����������ݣ���һ��Ϊ��������������Ϊ����ֵ��
	 * @param file
	 * @param targetSheet
	 * @return
	 */
	public List<String[]> readExcel(String file, Object targetSheet);
	
	/**
	 * read excel file
	 * @param file
	 * @return Map<String, List<String[]>>:key is sheetname,value is sheet content every row
	 */
	public Map<String, List<String[]>> readExcel(String file);
	
	/**
	 * get primary key specify table
	 * @param tableName
	 * @return
	 */
	public List<String> getPrimaryKeys(String tableName);
}
