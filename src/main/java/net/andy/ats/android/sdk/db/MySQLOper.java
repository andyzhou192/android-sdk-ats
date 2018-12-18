package net.andy.ats.android.sdk.db;

import java.sql.ResultSet;

public interface MySQLOper {

	/**
	 * 执行查询操作
	 * @param url
	 * @param sql
	 * @return
	 */
	public ResultSet executeQuery(String url, String sql);
	
	/**
	 * 执行更新操作
	 * @param url
	 * @param sql
	 * @return
	 */
	public int executeUpdate(String url, String sql);
	
	/**
	 * 根据数据文件dataFile初始化MySQL数据库
	 * @param url 数据库连接
	 * @param dataFile 数据文件
	 */
	public void insertDB(String url, String dataFile);
}
