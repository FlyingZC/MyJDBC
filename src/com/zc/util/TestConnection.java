package com.zc.util;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 测试数据库联通
 */
public class TestConnection
{
    private static final String propFileName="oracle.properties";
    public static void main(String[] args) throws  Exception
    {
        Properties prop=new Properties();
        prop.load(new FileInputStream(propFileName));
        String className=prop.getProperty("driverClass");
        String url=prop.getProperty("url");
        String user=prop.getProperty("user");
        String password=prop.getProperty("psw");
        Class.forName(className);
        Connection conn = DriverManager.getConnection(url,user,password);
        System.out.println(conn);
    }
    /*public static void main(String[] args) throws  Exception
    {
        String className="com.mysql.jdbc.Driver";
        String url="jdbc:mysql://localhost:3306/xzithis5?useUnicode=true&amp;amp;characterEncoding=utf-8";
        String user="root";
        String password="1262";
        Class.forName(className);
        Connection conn = DriverManager.getConnection(url,user,password);
        System.out.println(conn);
    }*/
}

