package net.andy.ats.android.sdk.db.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cmcc.ats.android.sdk.Constants;
import com.cmcc.ats.android.sdk.db.MySQLOper;
import com.cmcc.ats.android.sdk.tools.StringTools;
import com.cmcc.ats.android.sdk.util.Excel2003Handler;
import com.cmcc.ats.android.sdk.util.LogUtils;
import com.cmcc.ats.android.sdk.util.impl.Excel2003HandlerImpl;


public class MySQLOperImpl implements MySQLOper {
	
	public ResultSet executeQuery(String url, String sql){
		Connection conn = getConnection(url);
		try {
			return conn.prepareStatement(sql).executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	 
	public int executeUpdate(String url, String sql){
		Connection conn = getConnection(url);
		try {
			return conn.prepareStatement(sql).executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	private Connection getConnection(String url){
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			return DriverManager.getConnection(url);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据数据文件dataFile初始化MySQL数据库
	 * @param url 数据库连接
	 * @param dataFile 数据文件
	 */
	public void insertDB(String url, String dataFile){
		Connection conn = getConnection(url);
		try {
            Excel2003Handler excHandler = new Excel2003HandlerImpl();
    		Map<String, List<String[]>> dataMap = excHandler.readExcel(dataFile);
            
            for(String tableName : dataMap.keySet()){
            	List<String[]> dataList = dataMap.get(tableName);
            	List<String> primaryKeys = excHandler.getPrimaryKeys(tableName);
            	
            	String columns = (StringTools.array2String(dataList.get(0), Constants.COMMA)).toUpperCase();
            	String values = "VALUES (";
            	for(int i = 0; i < dataList.get(0).length; i++){
            		values += (Constants.QUES + Constants.COMMA);
            	}
            	values = values.substring(0, values.length()-1) + ")";
            	String selectSql = "SELECT " + columns + " FROM " + tableName;
            	String insertSql = "INSERT INTO " + tableName + " (" + columns + ") " + values;
            	
            	//获取数据库中各字段数据类型
            	ResultSetMetaData rsme = conn.prepareStatement(selectSql).executeQuery().getMetaData();
                Map<String, Integer> fielsTypes = new HashMap<String, Integer>();
                for (int i = 1; i <= rsme.getColumnCount() ; i++) {
                	fielsTypes.put(rsme.getColumnName(i), rsme.getColumnType(i));
                }
                
                if(!primaryKeys.isEmpty()){
                	String primaryKey = (StringTools.list2String(primaryKeys, Constants.EQUAL + Constants.QUES + " AND ")).toUpperCase().trim();
                	String condition = " WHERE " + primaryKey.substring(0, primaryKey.length()-3);
                	String deleteSql = "DELETE FROM " + tableName + condition;
                	PreparedStatement delps = conn.prepareStatement(deleteSql);   
                    for(int j = 1; j < dataList.size(); j++){
                    	setParams(delps, fielsTypes, primaryKeys.toArray(), dataList.get(j));
                    	int delCount = delps.executeUpdate();
                    	LogUtils.debug(this.getClass(), "----deleted " + delCount + " records.");
                    }
                }
                
                PreparedStatement insps = conn.prepareStatement(insertSql);   
                for(int j = 1; j < dataList.size(); j++){
                	setParams(insps, fielsTypes, dataList.get(0), dataList.get(j));
                	int updateCount = insps.executeUpdate();
                	LogUtils.debug(this.getClass(), "----updated " + updateCount + " records.");
                }
            }
            
        } catch (Exception e) {   
            e.printStackTrace();   
        } finally {
        	// 关闭连接对象
    		if (conn != null) { 
    			try {
    				conn.close();
    			} catch (SQLException e) {
    				e.printStackTrace();
    			}
    		}
        } 
	}
	
	/**
	 * 设置sql中的参数
	 * @param ps
	 * @param filedInfo
	 * @param fields
	 * @param values
	 */
	private void setParams(PreparedStatement ps, Map<String, Integer> filedInfo,  Object[] fields, String[] values) {
		for(int i = 0; i < fields.length; i++) {
			try {
				int index = i + 1; //SQL中传参数的下标是从1开始的
				switch (filedInfo.get(fields[i].toString().trim())) {
				case Types.BIGINT:
					ps.setLong(index, Long.valueOf(values[i]));
					break;
				case Types.BOOLEAN:
					ps.setBoolean(index, Boolean.valueOf(values[i]));
					break;
				case Types.TIME:
					ps.setTime(index, Time.valueOf(values[i]));
					break;
				case Types.DATE:
					ps.setDate(index, Date.valueOf(values[i]));
					break;
				case Types.TIMESTAMP:
					ps.setTimestamp(index, Timestamp.valueOf(values[i]));
					break;
				case Types.DOUBLE:
					ps.setDouble(index, Double.valueOf(values[i]));
					break;
				case Types.FLOAT:
					ps.setFloat(index, Float.valueOf(values[i]));
					break;
				case Types.INTEGER:
					ps.setInt(index, Integer.valueOf(values[i]));
					break;
				case Types.SMALLINT:
				case Types.TINYINT:
					ps.setShort(index, Short.valueOf(values[i]));
					break;
				case Types.NCHAR:
				case Types.VARCHAR:
				case Types.NVARCHAR:
					ps.setString(index, values[i]);
					break;
				case Types.BIT:
					ps.setByte(index, Byte.valueOf(values[i]));
					break;
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
