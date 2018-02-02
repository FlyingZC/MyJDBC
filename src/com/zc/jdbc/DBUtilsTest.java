package com.zc.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.junit.Test;

import com.zc.at.JDBCTools;

public class DBUtilsTest {
	QueryRunner queryRunner=new QueryRunner();
	class MyResultSetHandler implements ResultSetHandler{
		
	
		public Object handle(ResultSet rs) throws SQLException {
			// TODO Auto-generated method stub
			
			System.out.println("handle...");
			List<User> users=new ArrayList<User>();
			while(rs.next()){
				String name=rs.getString(1);
				String psw=rs.getString(2);
				User user=new User(name,psw);
				users.add(user);
			}
			
			
			return users;
		}
		
	}
	
	@Test
	public void testQueryRunnerUpdate()throws Exception {
		//测试QueryRunner类的update方法 
		//创建QueryRunner的实现类
		QueryRunner queryRunner=new QueryRunner();
		//2.使用其update方法
		String sql="DELETE FROM USER WHERE NAME=?";
		Connection connection=null;
		try {
			connection=JDBCTools.getConnection();
			//填充占位符.执行更新(删除)
			queryRunner.update(connection,sql,"zzc");
		} catch (Exception e) {
			// TODO: handle exception
			//调用jdbcTools工具类里的释放连接
			JDBCTools.releaseDB(null, null, connection);
		}
		
	}
	
	@Test
	public void testQueryRunnerQuery(){
		QueryRunner queryRunner=new QueryRunner();
		Connection connection=null;
		try {
			connection=JDBCTools.getConnection();
			String sql="select * from user";
			//ResultSetHandler rsh传一个ResultSetHandler的对象
			Object object=queryRunner.query(connection, sql, new MyResultSetHandler());
			//输出这是MyResultSetHandler类里的handle()方法的返回值
			//即ResultSetHandler实现类里的handler()方法的返回值作为了该查询的返回值
			System.out.println(object);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Test
	public void testBeanHandler(){
		Connection conn=null;
		try {
			conn=JDBCTools.getConnection();
			String sql="select * from user where name=?";
			//只返回第一条或者null
			User user=queryRunner.query(conn,sql,new BeanHandler(User.class),"zzx");
			System.out.println(user);
			
		} catch (Exception e) {
			// TODO: handle exception
			JDBCTools.releaseDB(null, null, conn);
		}
	}
	
	@Test
	public void testBeanListHandler(){
		Connection conn=null;
		try {
			conn=JDBCTools.getConnection();
			String sql="select * from user where name=?";
			
			//BeanListHandler()把结果集转换为一个List.该List不为null.但可能为空集合
			List<User> users=queryRunner.query(conn,sql,new BeanListHandler(User.class),"zzx");
			System.out.println(users);
			
		} catch (Exception e) {
			// TODO: handle exception
			JDBCTools.releaseDB(null, null, conn);
		}
	}

	
	@Test
	public void testMapHandler(){
		Connection conn=null;
		try {
			conn=JDBCTools.getConnection();
			String sql="select * from user";
			
			//BeanListHandler()把结果集转换为一个List.该List不为null.但可能为空集合. query最后均可以加占位符参数
			Map<String,Object> result=queryRunner.query(conn,sql,new MapHandler());
			//只返回一条记录{name=zzx, pass=111}
			System.out.println(result);
			
		} catch (Exception e) {
			// TODO: handle exception
			JDBCTools.releaseDB(null, null, conn);
		}
	}
	
	@Test
	public void testMapListHandler(){
		Connection conn=null;
		try {
			conn=JDBCTools.getConnection();
			String sql="select * from user";
			
			//BeanListHandler()把结果集转换为一个List.该List不为null.但可能为空集合
			List<Map<String,Object>> result=queryRunner.query(conn,sql,new MapListHandler());
			//[{name=zzx, pass=111}, {name=zzz, pass=123}]
			System.out.println(result);
			
		} catch (Exception e) {
			// TODO: handle exception
			JDBCTools.releaseDB(null, null, conn);
		}
	} 

}
