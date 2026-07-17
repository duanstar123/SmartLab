package com.dao.Impl;

import com.dao.UserDao;
import com.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    @Override
    public User findUserById(String studentId) {
        User user = null;
        try {
            Connection conn = com.dao.JDBCTools.getConnection();
            String sql = "SELECT * FROM user WHERE student_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, studentId);
            ResultSet rs = ps.executeQuery();
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
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public int save(User user) {
        int result = 0;
        try {
            Connection conn = com.dao.JDBCTools.getConnection();
            String sql = "INSERT INTO user(student_id, name, gender, password, grade, college_name, department_name, class_name) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getStudentId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getGender());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getGrade());
            ps.setString(6, user.getCollegeName());
            ps.setString(7, user.getDepartmentName());
            ps.setString(8, user.getClassName());
            result = ps.executeUpdate();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int delete(String studentId) {
        int result = 0;
        try {
            Connection conn = com.dao.JDBCTools.getConnection();
            String sql = "DELETE FROM user WHERE student_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, studentId);
            result = ps.executeUpdate();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            Connection conn = com.dao.JDBCTools.getConnection();
            String sql = "SELECT * FROM user";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
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
            rs.close();
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
}