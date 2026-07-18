package com.dao.Impl;
import com.dao.BaseDao;
import com.dao.AdminDao;
import com.entity.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminDaoImpl implements AdminDao {
    @Override
    public Admin findByAdminId(Admin admin) {
        Admin admin1 = null;
        try {
            BaseDao dao = new BaseDao();
            String sql = "select * from admin where admin_id=?";
            admin1 = dao.getBean(Admin.class, sql, admin.getAdminId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return admin1;
    }

    @Override
    public Admin findByUsername(Admin admin) {
        Admin admin1 = null;
        try {
            BaseDao dao = new BaseDao();
            String sql = "select * from admin where username=?";
            admin1 = dao.getBean(Admin.class, sql, admin.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return admin1;
    }

    @Override
    public int insertAdmin(Admin admin) {
        int rs = 0;
        try {
            BaseDao dao = new BaseDao();
            String sql = "insert into admin(admin_id, username, password, phone, email) values(?, ?, ?, ?, ?)";
            rs = dao.update(sql, admin.getAdminId(), admin.getUsername(), admin.getPassword(), admin.getPhone(), admin.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    @Override
    public Admin loginAdmin(Admin admin) {
        Admin admin1 = null;
        try {
            // 添加调试信息
            System.out.println("=== AdminDaoImpl.loginAdmin ===");
            System.out.println("查询条件 - admin_id: " + admin.getAdminId());
            System.out.println("查询条件 - password: " + admin.getPassword());

            // 使用原生JDBC测试查询
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            try {
                conn = com.dao.JDBCTools.getConnection();
                String sql = "select * from admin where admin_id=? and password=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, admin.getAdminId());
                ps.setString(2, admin.getPassword());

                System.out.println("执行SQL: " + sql);

                rs = ps.executeQuery();

                if (rs.next()) {
                    System.out.println("查询成功，找到管理员！");
                    System.out.println("admin_id: " + rs.getString("admin_id"));
                    System.out.println("username: " + rs.getString("username"));

                    // 手动创建Admin对象
                    admin1 = new Admin();
                    admin1.setAdminId(rs.getString("admin_id"));
                    admin1.setUsername(rs.getString("username"));
                    admin1.setPassword(rs.getString("password"));
                    admin1.setPhone(rs.getString("phone"));
                    admin1.setEmail(rs.getString("email"));
                } else {
                    System.out.println("查询失败，未找到匹配的管理员");
                }
            } finally {
                try { if (rs != null) rs.close(); } catch (Exception e) {}
                try { if (ps != null) ps.close(); } catch (Exception e) {}
                // 连接由 ThreadLocal 统一管理，这里不关闭 conn
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return admin1;
    }
}