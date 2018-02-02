package com.zc.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;

import com.mysql.jdbc.ResultSet;

/**
 * @author flyingzc
 * 可用
 */
public class JdbcUtil {
	// 数据库连接池
	private static BasicDataSource bs;
	private static final String ddalName = "mysql.properties";
	static {
		Properties pros = new Properties();
		try {
			pros.load(new FileInputStream(ddalName));
			String driverClassName = pros.getProperty("driverClassName");
			String url = pros.getProperty("url");
			String username = pros.getProperty("uername");
			String password = pros.getProperty("password");
			int maxActive = Integer.parseInt(pros.getProperty("maxActive"));
			long maxWait = Long.parseLong(pros.getProperty("maxWait"));
			//
			bs = new BasicDataSource();
			bs.setDriverClassName(driverClassName);
			bs.setUrl(url);
			bs.setUsername(username);
			bs.setPassword(password);
			bs.setMaxActive(maxActive);
			bs.setMaxWait(maxWait);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException {
		return bs.getConnection();
	}

	public static void releaseDB(ResultSet rs, PreparedStatement psmt, Connection conn) throws SQLException {
		if (rs != null) {
			rs.close();
		}
		if (psmt != null) {
			psmt.close();
		}
		if (conn != null) {
			conn.close();
		}
	}

	public static void main(String[] args) throws SQLException {
		System.out.println(JdbcUtil.getConnection());
	}
}
