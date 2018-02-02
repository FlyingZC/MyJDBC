package com.zc.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TMetaData {
	public static void main(String[] args) throws SQLException {
		Connection conn=JdbcTools.getConnection();
		DatabaseMetaData meta=conn.getMetaData();
		//可以查看数据库本身的一些基本信息
		//获取数据库版本号
		int version=meta.getDatabaseMajorVersion();
		System.out.println(version);//5
		//得到连接到数据库的用户名
		String user=meta.getUserName();//root@localhost
		System.out.println(user);
		//得到mysql中有哪些数据库
		ResultSet rs=meta.getCatalogs();
		while(rs.next()){//该结果集封装的是所有表的名字
			System.out.println(rs.getString(1));//db_book....
		}
		
	}
}
