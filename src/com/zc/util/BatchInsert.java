package com.zc.util;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.Test;
/*批量插入工具,先修改连接*/
public class BatchInsert {
	public static void main(String[] args) throws SQLException {
		Connection conn=JdbcUtil.getConnection();
		conn.setAutoCommit(false);
		String sql="insert into student values(?,?,?,?,?,?);";
		PreparedStatement psmt = conn.prepareStatement(sql);
		for(int i=0;i<100;i++){
			psmt.setInt(1, i);
			psmt.setString(2, RandomUtils.randName());
			psmt.setString(3, RandomUtils.randSex());
			psmt.setDate(4, (Date) RandomUtils.randDate());
			psmt.setInt(5, RandomUtils.randNum(1, 5));
			psmt.setInt(6, RandomUtils.randAge());
			psmt.addBatch();
		}
		psmt.executeBatch();
		conn.commit();
		System.out.println("插入成功");
	}
	
	@Test
	public void test01() throws SQLException{
		Connection conn=JdbcUtil.getConnection();
		conn.setAutoCommit(false);
		String sql="insert into user_role values(?,?)";
		PreparedStatement psmt = conn.prepareStatement(sql);
		for(int i=0;i<300;i++){
			psmt.setInt(1, i);
			psmt.setInt(2, RandomUtils.randNum(1, 5));
			psmt.addBatch();
		}
		psmt.executeBatch();
		conn.commit();
		System.out.println("插入成功");
	}
	
	
	
}
