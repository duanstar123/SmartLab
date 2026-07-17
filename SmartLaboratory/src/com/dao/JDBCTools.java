//package com.dao;
//
//import com.alibaba.druid.pool.DruidDataSourceFactory;
//
//import javax.sql.DataSource;
//import java.sql.Connection;
//import java.util.Properties;
//
//public class JDBCTools {
//    /*连接池对象*/
//    private static DataSource ds;
//    /*本地线程*/
//    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();
//    static {
//        try{
//            //加载数据库连接池配置文件
//            Properties prop = new Properties();
//            prop.load(JDBCTools.class.getClassLoader().getResourceAsStream("druid.properties"));
//            //获取连接池对象
//            ds = DruidDataSourceFactory.createDataSource(prop);
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }
//    public static Connection getConnection()throws Exception{
//        Connection conn =threadLocal.get();
//        if(conn==null){
//            conn = ds.getConnection();
//            threadLocal.set(conn);
//        }
//        return conn;
//    }
//    public static void freeConnection(Connection conn)throws Exception{
//        Connection conn1=threadLocal.get();
//        if(conn1!=null){
//            threadLocal.remove();
//            conn1.close();
//        }
//    }
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
        try {
            //加载数据库连接池配置文件
            Properties prop = new Properties();
            prop.load(JDBCTools.class.getClassLoader().getResourceAsStream("druid.properties"));
            //获取连接池对象
            ds = DruidDataSourceFactory.createDataSource(prop);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws Exception {
        Connection conn = threadLocal.get();
        if (conn == null) {
            conn = ds.getConnection();
            threadLocal.set(conn);
        }
        return conn;
    }

    public static void freeConnection(Connection conn) throws Exception {
        Connection conn1 = threadLocal.get();
        if (conn1 != null) {
            threadLocal.remove();
            // 确保数据写入数据库
            if (!conn1.getAutoCommit()) {
                conn1.commit();
            }
            conn1.close();
        }
    }

    // 设置手动事务
    public static void beginTransaction() throws Exception {
        Connection conn = getConnection();
        conn.setAutoCommit(false);
    }

    // 提交事务
    public static void commitTransaction() throws Exception {
        Connection conn = threadLocal.get();
        if (conn != null && !conn.getAutoCommit()) {
            conn.commit();
        }
    }

    // 回滚事务
    public static void rollbackTransaction() throws Exception {
        Connection conn = threadLocal.get();
        if (conn != null && !conn.getAutoCommit()) {
            conn.rollback();
        }
    }
}