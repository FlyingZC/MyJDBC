package com.zc.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 获取主键值
 * @author zc
 */
public class T02Pk {
	public static void main(String[] args) throws SQLException {
		Connection conn=JdbcTools.getConnection();
		String sql="insert into customer(name,email,birth) values(?,?,?)";
		try {
			//PreparedStatement psmt = conn.prepareStatement(sql);
			//返回自动生成的主键(类似于mysql中使用自增的方式产生的主键auto_increment)
			//获取新生成的主键
			PreparedStatement psmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			psmt.setString(1,"zc");
			psmt.setString(2, "12@qq.com");
			psmt.setDate(3, 
					new java.sql.Date(new java.util.Date().getTime()));
			psmt.executeUpdate();
			//该结果集是psmt的结果集.不是查询的结果集
			//该结果集只有一列，GENERATE_KEY用于存放新生成的主键值
			ResultSet rs = psmt.getGeneratedKeys();
			while(rs.next()){
				System.out.println(rs.getObject(1));
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
