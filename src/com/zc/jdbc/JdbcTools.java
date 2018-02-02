package com.zc.jdbc;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;



public class JdbcTools {
	static{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static void update(String sql,Object...args){
		Connection conn;
		try {
			conn = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/db_testjdbc","root","1262");
			PreparedStatement psmt=conn.prepareStatement(sql);
			//JDBCTools
			for(int i=0;i<args.length;i++){
				psmt.setObject(i+1,args[i]);
			}
			psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static <T> T get(Class<T> clazz,String sql,Object...args){
		T entity=null;
		Connection conn;
		try {
			conn = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/db_testjdbc","root","1262");
			PreparedStatement psmt=conn.prepareStatement(sql);
			for(int i=0;i<args.length;i++){
				psmt.setObject(i+1,args[i]);
			}
			ResultSet rs=psmt.executeQuery();
			Map<String,Object> resultMap=new HashMap<String,Object>();
			while(rs.next()){
				//1.获取ResultSetMetaData对象
				ResultSetMetaData meta=rs.getMetaData();
				for(int i=0;i<meta.getColumnCount();i++){//获取列数量
					String columnLabel=meta.getColumnLabel(i+1);//获取列别名(一行)
					Object columnValue=rs.getObject(columnLabel);
					resultMap.put(columnLabel, columnValue);
					System.out.println(columnLabel);
				}
				//反射创建对象
				entity=clazz.newInstance();
				//通过解析sql语句来判断到底查询了哪些列,以及需要为entity对象
				//遍历map
				for(Map.Entry<String,Object> entry:resultMap.entrySet()){
					//属性名
					String fieldName=entry.getKey();
					//
					Object fieldValue=entry.getValue();
					System.out.println(fieldName+":"+fieldValue);
					//ReflectionUtils.setFieldValue(entity, fieldName, fieldValue);
					
					Field field=clazz.getDeclaredField(fieldName);//获取指定属性
					field.setAccessible(true);//变为可访问
					field.set(entity, fieldValue);//给entity中的该属性赋值
					
					System.out.println(entity);
				}
			}
		} catch (SQLException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return entity;
	}

	public static void releaseDB(ResultSet rs, PreparedStatement psmt,
			Connection conn) {
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(psmt!=null){
			try {
				psmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
			
	}

	/**获取数据库连接
	 * @return
	 */
	public static Connection getConnection() {
		Connection conn=null;
		try {
			conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/db_testjdbc","root","1262");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
}
