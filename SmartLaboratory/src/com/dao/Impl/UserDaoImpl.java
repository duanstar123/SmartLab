package com.dao.Impl;

import com.dao.UserDao;
import com.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    // 直接使用 JDBC 连接，避免 ThreadLocal 问题
    private Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/lab?useSSL=false&serverTimezone=Asia/Shanghai&characterEncoding=utf8",
                "root",
                "root"
        );
    }

    @Override
    public User findUserById(String studentId) {
        User user = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            String sql = "SELECT * FROM user WHERE student_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, studentId);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setStudentId(rs.getString("student_id"));
                user.setName(rs.getString("name"));
                user.setGender(rs.getString("gender"));
                user.setPassword(rs.getString("password"));
                user.setGrade(rs.getString("grade"));
                user.setCollegeName(rs.getString("college_name"));
                user.setDepartmentName(rs.getString("department_name"));
                user.setClassName(rs.getString("class_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return user;
    }

    @Override
    public int save(User user) {
        int result = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            String sql = "INSERT INTO user(student_id, name, gender, password, grade, college_name, department_name, class_name) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getStudentId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getGender());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getGrade());
            ps.setString(6, user.getCollegeName());
            ps.setString(7, user.getDepartmentName());
            ps.setString(8, user.getClassName());
            result = ps.executeUpdate();
            System.out.println("保存成功: " + user.getStudentId() + " - " + user.getName());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return result;
    }

    @Override
    public int delete(String studentId) {
        int result = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            String sql = "DELETE FROM user WHERE student_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, studentId);
            result = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return result;
    }

    @Override
    public List<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            String sql = "SELECT * FROM user";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setStudentId(rs.getString("student_id"));
                user.setName(rs.getString("name"));
                user.setGender(rs.getString("gender"));
                user.setPassword(rs.getString("password"));
                user.setGrade(rs.getString("grade"));
                user.setCollegeName(rs.getString("college_name"));
                user.setDepartmentName(rs.getString("department_name"));
                user.setClassName(rs.getString("class_name"));
                users.add(user);
            }
            System.out.println("查询到 " + users.size() + " 个用户");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return users;
    }
}