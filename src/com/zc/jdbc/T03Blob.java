package com.zc.jdbc;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

import org.junit.Test;

import com.zc.at.JDBCTools;

/**
 * 向数据库中插入大数据 错误！！！
 * @author zc
 *
 */
public class T03Blob {
	public static void main(String[] args) {
		T03Blob b=new T03Blob();
		b.testInsertBlob();
	}
	/**
	 * 插入 BLOB 类型的数据必须使用 PreparedStatement：因为 BLOB 类型
	 * 的数据时无法使用字符串拼写的。
	 * 
	 * 调用 setBlob(int index, InputStream inputStream)
	 */
	public void testInsertBlob(){
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		/*
		 * mysql中blob是一个二进制大型对象，可存储大量数据的容器，能容纳不同大小的数据
		 * TinyBlob:最大255字节
		 * Blob:最大65k
		 * MediumBlob:最大16m
		 * LongBlob:最大4G
		 * */
		try {
			connection = JdbcTools.getConnection();
			String sql = "INSERT INTO customer(name, email, birth, picture)" 
					+ "VALUES(?,?,?,empty_blob())";
			preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setString(1, "ABCDE");
			preparedStatement.setString(2, "abcde@zc.com");
			preparedStatement.setDate(3, 
					new Date(new java.util.Date().getTime()));
			//插入blob类型数据
			InputStream inputStream = new FileInputStream("邻接表.png");
			preparedStatement.setBlob(4, inputStream);
			
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			JdbcTools.releaseDB(null, preparedStatement, connection);
		}
	}
}
