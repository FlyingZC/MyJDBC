package com.zc.jdbc;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;

public class Dao {
	//insert,update,delete操作均可视为update(类似于dbutil)
	/**
	 * @param sql 要操作的(update,insert,delete)sql
	 * @param args 填充sql中的占位符
	 */
	public void update(String sql,Object...args){
		Connection conn=null;
		PreparedStatement psmt=null;
		try {
			psmt=conn.prepareStatement(sql);
			for(int i=0;i<args.length;i++){
				psmt.setObject(i+1,args[i]);//占位符下标从1开始
			}
			psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JdbcTools.releaseDB(null,psmt,conn);
		}
	}
	
	//查询一条记录,返回对应的对象
	public <T>T getBean(Class<T> clazz,String sql,Object...args){
		return null;
	}
	
	//查询多条记录,返回一个存储多个Bean的List
	public <T> List<T> getBeanList(Class<T> clazz,String sql) throws Exception{
		List<T> list=new ArrayList<T>();
		Connection conn=null;
		PreparedStatement psmt=null;		
		ResultSet rs=null;
		try {
			conn=JdbcTools.getConnection();
			psmt=conn.prepareStatement(sql);
			rs=psmt.executeQuery();
			if(rs!=null){//这里不适用rs.next().因为不遍历.每次调用next(),指针会后移
				List<Map<String,Object>> resultMapList=handleResultSetToMapList(rs);
				list=transferMapListToBeanList(clazz,resultMapList);//将List<Map<String,Object>>转换为
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			JdbcTools.releaseDB(rs, psmt, conn);
		}
		return list;
	}

	/**
	 * @param resultMapList
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws InvocationTargetException 
	 */
	private <T>List<T> transferMapListToBeanList(Class<T> clazz, List<Map<String, Object>> resultMapList) throws Exception {
		List<T> list=new ArrayList<T>();
		T bean=null;
		if(resultMapList.size()>0){
			for(Map<String,Object> map:resultMapList){
				bean=clazz.newInstance();
				for(Entry<String,Object> entry:map.entrySet()){
					String key=entry.getKey();
					Object value=entry.getValue();
					
					Field field=clazz.getDeclaredField(key);//获取指定属性
					field.setAccessible(true);//变为可访问
					field.set(bean,value);//给entity中的该属性赋值
				}
				list.add(bean);
			}
		}
		return list;
	}

	public List<Map<String,Object>> getMapList(String sql) throws SQLException{
		Connection conn=null;
		PreparedStatement psmt=null;		
		ResultSet rs=null;
		conn=JdbcTools.getConnection();
		psmt=conn.prepareStatement(sql);
		rs=psmt.executeQuery();
		return handleResultSetToMapList(rs);//调用本类中的方法
	}
	
	/**
	 * 将结果集转换为List<Map<String,Object>>类型,其中Map表示每一行的数据,List则表示多列
	 * @param rs
	 * @return
	 * @throws SQLException 
	 */
	public List<Map<String, Object>> handleResultSetToMapList(ResultSet rs) throws SQLException {
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		//定义该方法 获取所有列的别名
		List<String> colLabs=getColumnLabels(rs);
		//保存每一行数据的Map
		Map<String,Object> rowVals=null;
		while(rs.next()){
			rowVals=new HashMap<String,Object>();
			for(String colLab:colLabs){//遍历每一行
				Object value=rs.getObject(colLab);
				rowVals.put(colLab, value);
			}
			//每一行遍历完,将map放入list中
			list.add(rowVals);
		}
		return list;
	}

	/**获取所有列的别名
	 * @param rs
	 * @throws SQLException 
	 */
	private List<String> getColumnLabels(ResultSet rs) throws SQLException {
		List<String> columnLabels=new ArrayList<String>();
		ResultSetMetaData meta=rs.getMetaData();//结果集的元数据
		for(int i=0;i<meta.getColumnCount();i++){//元数据获取列数
			columnLabels.add(meta.getColumnLabel(i+1));//下标从1开始获取列的别名
		}
		return columnLabels;
	}
	
}
