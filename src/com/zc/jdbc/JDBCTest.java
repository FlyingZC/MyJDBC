package com.zc.jdbc;
import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.Test;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zc.at.JDBCTools;

/**
	 * 1. 创建 c3p0-config.xml 文件, 
	 * 参考帮助文档中 Appendix B: Configuation Files 的内容
	 * 2. 创建 ComboPooledDataSource 实例；
	 * DataSource dataSource = 
	 *			new ComboPooledDataSource("helloc3p0");  
	 * 3. 从 DataSource 实例中获取数据库连接. 
	 */
public class JDBCTest{
	//测试使用配置文件的c3p0的连接
	@Test
	public void testC3poWithConfigFile() throws Exception{
		DataSource dataSource = 
				new ComboPooledDataSource("helloc3p0");  
		
		//输出com.mchange.v2.c3p0.impl.NewProxyConnection@367d438
		System.out.println(dataSource.getConnection()); 
		
		ComboPooledDataSource comboPooledDataSource = 
				(ComboPooledDataSource) dataSource;
		//20
		System.out.println(comboPooledDataSource.getMaxStatements()); 
	}
	//测试调用JDBCTools里的使用c3p0的getConnection()
	@Test
	public void testJdbcTools() throws Exception{
		Connection connection=JDBCTools.getConnection();
		System.out.println("heh"+connection);
	}
	
}
	