package com.zc.util;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

/**
 * @author flyingzc
 * TODO 有毒,勿用
 */
public class DBCPUtil
{
    private static DataSource datasource;
    static
    {
        try
        {
            //TODO 有问题,获取不到
            String rootPath = DBCPUtil.class.getClassLoader().getResource("//").getPath();
            InputStream in = DBCPUtil.class.getClassLoader().getResourceAsStream(rootPath + "mysql.properties");
            Properties props = new Properties();
            //加载配置
            props.load(in);
            //获取数据源
            datasource = BasicDataSourceFactory.createDataSource(props);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ExceptionInInitializerError("初始化数据库配置文件失败");
        }
    }

    //获取数据源
    public static DataSource getDataSource()
    {
        return datasource;
    }

    //获取数据库连接
    public static Connection getConnection()
    {
        try
        {
            return datasource.getConnection();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException("从数据源获取连接失败");
        }
    }

    public static void main(String[] args)
    {
        System.out.println(DBCPUtil.getConnection());
    }
}
