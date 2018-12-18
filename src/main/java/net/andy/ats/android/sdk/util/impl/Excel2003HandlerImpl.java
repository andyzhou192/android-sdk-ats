package net.andy.ats.android.sdk.util.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.andy.ats.android.sdk.tools.StringTools;
import net.andy.ats.android.sdk.util.Excel2003Handler;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * excel2003��ȡ
 * @author sifuma@163.com
 *
 */
public class Excel2003HandlerImpl implements Excel2003Handler {
	
	private Map<String, List<String>> primaryKeysMap = new HashMap<String, List<String>>();
	
	public List<String> getPrimaryKeys(String tableName) {
		return primaryKeysMap.get(tableName + "Primary");
	}

	/**
	 * read excel file
	 * @param file
	 * @return Map<String, List<String[]>>:key is sheetname,value is sheet content every row
	 */
	public Map<String, List<String[]>> readExcel(String file){
		String prePath = Thread.currentThread().getContextClassLoader().getResource("").getPath(); 
		Map<String, List<String[]>> resultMap = new HashMap<String, List<String[]>>();
        try {
            InputStream mInputStream = new FileInputStream(prePath + file);
            Workbook wb = Workbook.getWorkbook(mInputStream);
            for(String sheetName : wb.getSheetNames()){
				List<String> primaryKeys = new ArrayList<String>();
            	Sheet mSheet = wb.getSheet(sheetName);
            	int rowCou = getRowCount(mSheet);
            	int colCou = getColCount(mSheet);
            	List<String[]> rowList = new ArrayList<String[]>();
            	for(int i= 0 ; i < rowCou ; i ++){
            		String[] colArray = new String[colCou];
            		for(int j = 0 ; j < colCou ; j ++){
            			Cell cell = mSheet.getCell(j, i);
            			String content = cell.getContents();
            			colArray[j] = content;
            			//获取主键
            			if(i == 0 && 0 < cell.getCellFormat().getFont().getUnderlineStyle().getValue()){
            				primaryKeys.add(content);
            			}
            		}
            		rowList.add(colArray);
            	}
            	resultMap.put(sheetName, rowList);
            	primaryKeysMap.put(sheetName + "Primary", primaryKeys);
            }
            wb.close();
            mInputStream.close();
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		return resultMap;
    }

	/**
	 * ��ȡexcel�г���һ��֮����������ݣ���һ��Ϊ��������������Ϊ����ֵ��
	 * @param file
	 * @param targetSheet
	 * @return
	 */
	public List<String[]> readExcel(String file, Object targetSheet){
		String prePath = Thread.currentThread().getContextClassLoader().getResource("").getPath(); 
		//System.out.println("====================" + prePath);
		List<String[]> rowList = new ArrayList<String[]>();
        try {
            InputStream mInputStream = new FileInputStream(prePath + file);
            Workbook wb = Workbook.getWorkbook(mInputStream);wb.getSheetNames();
            Sheet mSheet = null;
            if(null == targetSheet)
            	targetSheet = 0;
            if(targetSheet instanceof Integer){
            	mSheet = wb.getSheet((Integer) targetSheet);
            }else{
            	mSheet = wb.getSheet((String) targetSheet);
            }
            int rowCou = getRowCount(mSheet);
            int colCou = getColCount(mSheet);
            //LogUtils.debug(Excel2003HandlerImpl.class, "Total Row: " + rowCou + ", Total Columns: " + colCou);
            for(int i= 0 ; i < rowCou ; i ++){
            	String[] colArray = new String[colCou];
                for(int j = 0 ; j < colCou ; j ++){
                    Cell temp = mSheet.getCell(j, i);
                    String content = temp.getContents();
                    //LogUtils.debug(Excel2003HandlerImpl.class, j + " ," + i + " ," + content);
                    colArray[j] = content;
                }
                rowList.add(colArray);
            }
            wb.close();
            mInputStream.close();
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		return rowList;
    }
	
	
	/**
	 * 鑾峰彇绗竴涓涓�鍒�?崟鍏冩牸鍐呭闈炵┖鐨勮鍙�?,鍥犱负绗竴鍒椾负caseId锛屼笉鑳戒负绌�
	 * @param mSheet
	 * @return
	 */
	private static int getRowCount(Sheet mSheet){
		int count = 0;
		int row = mSheet.getRows();
		for(int i=0; i<row; i++){
			String content = mSheet.getCell(0, i).getContents();
			if(StringTools.isEmpty(content)){
				break;
			}
			++count;
		}
		return count;
	}
	
	/**
	 * 鑾峰彇绗竴涓涓�琛屽崟鍏冩牸鍐呭闈炵┖鐨勫垪鍙凤紝鍥犱负绗竴琛屼负鍙傛暟key锛屼笉鑳戒负绌�
	 * @param mSheet
	 * @return
	 */
	private static int getColCount(Sheet mSheet){
		int count = 0;
		int columns = mSheet.getColumns();
		for(int i=0; i<columns; i++){
			String content = mSheet.getCell(i, 0).getContents();
			if(StringTools.isEmpty(content)){
				break;
			}
			++count;
		}
        return count;
	}
	
//	public static void main(String[] args) {
//		readExcel("D:/work/workspace/server-ats/src/main/resources/net/andy/ats/server/handle/test.xls",null);
//	}
}
