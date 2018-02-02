package com.zc.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

/**
 * 测试事务隔离级别
 * 比如测试读未提交(脏读)
 * update()方法中开启事务,更新数据,在conn.commit()时debug打断点,让数据update但是没提交
 * get()方法中读取更新的数据,可以读到未提交的数据
 * 
 * @author zc
 *
 */
public class T04Tran {
	
	//读
	@Test
	public void get() throws SQLException{
		Connection conn=JdbcTools.getConnection();
		//设置事务隔离级别,读未提交
		conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
		conn.setAutoCommit(false);
		String sql="select money from customer where id=2";
		PreparedStatement psmt = conn.prepareStatement(sql);
		ResultSet rs=psmt.executeQuery();
		while(rs.next()){
			int money=rs.getInt("money");
			System.out.println(money);
		}
		conn.commit();
	}
	@Test
	public void update() throws SQLException{
		Connection conn=JdbcTools.getConnection();
		//设置事务隔离级别,读未提交
		conn.setAutoCommit(false);
		String sql="update customer set money=money-100 where id=2";
		PreparedStatement psmt = conn.prepareStatement(sql);
		psmt.executeUpdate();
		conn.commit();
	}
	
	
}
