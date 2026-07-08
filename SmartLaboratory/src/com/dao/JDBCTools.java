package com.dao;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Properties;

public class JDBCTools {
    /*连接池对象*/
    private static DataSource ds;
    /*本地线程*/
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();
    static {
        try{
            //加载数据库连接池配置文件
            Properties prop = new Properties();
            prop.load(JDBCTools.class.getClassLoader().getResourceAsStream("druid.properties"));
            //获取连接池对象
            ds = DruidDataSourceFactory.createDataSource(prop);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static Connection getConnection()throws Exception{
        Connection conn =threadLocal.get();
        if(conn==null){
            conn = ds.getConnection();
            threadLocal.set(conn);
        }
        return conn;
    }
    public static void freeConnection(Connection conn)throws Exception{
        Connection conn1=threadLocal.get();
        if(conn1!=null){
            threadLocal.remove();
            conn1.close();
        }
    }
}
