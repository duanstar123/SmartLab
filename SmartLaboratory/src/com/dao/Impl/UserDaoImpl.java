package com.dao.Impl;

import com.dao.BaseDao;
import com.dao.UserDao;
import com.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDaoImpl implements UserDao {
    @Override
    public User findByUsername(User user) {
        User user1 = null;
        try {
            BaseDao dao = new BaseDao();
            String sql = "select * from user where student_id=?";
            user1 = dao.getBean(User.class, sql, user.getStudentId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user1;
    }

    @Override
    public int insertUSer(User user) {
        int rs = 0;
        try {
            BaseDao dao = new BaseDao();
            String sql = "insert into user(student_id, name, gender, password, grade, college_name, department_name, class_name) " +
                    "values(?, ?, ?, ?, ?, ?, ?, ?)";
            rs = dao.update(sql,
                    user.getStudentId(),
                    user.getName(),
                    user.getGender(),
                    user.getPassword(),
                    user.getGrade(),
                    user.getCollegeName(),
                    user.getDepartmentName(),
                    user.getClassName()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    @Override
    public User loginUser(User user) {
        User user1 = null;
        try {
            // 添加调试信息
            System.out.println("=== UserDaoImpl.loginUser ===");
            System.out.println("查询条件 - student_id: " + user.getStudentId());
            System.out.println("查询条件 - password: " + user.getPassword());

            // 使用原生JDBC测试查询
            Connection conn = null;
            PreparedStatement ps = null;
            ResultSet rs = null;

            try {
                conn = com.dao.JDBCTools.getConnection();
                String sql = "select * from user where student_id=? and password=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, user.getStudentId());
                ps.setString(2, user.getPassword());

                System.out.println("执行SQL: " + sql);

                rs = ps.executeQuery();

                if (rs.next()) {
                    System.out.println("查询成功，找到用户！");
                    System.out.println("student_id: " + rs.getString("student_id"));
                    System.out.println("name: " + rs.getString("name"));
                    System.out.println("password: " + rs.getString("password"));

                    // 手动创建User对象
                    user1 = new User();
                    user1.setStudentId(rs.getString("student_id"));
                    user1.setName(rs.getString("name"));
                    user1.setGender(rs.getString("gender"));
                    user1.setPassword(rs.getString("password"));
                    user1.setGrade(rs.getString("grade"));
                    user1.setCollegeName(rs.getString("college_name"));
                    user1.setDepartmentName(rs.getString("department_name"));
                    user1.setClassName(rs.getString("class_name"));
                } else {
                    System.out.println("查询失败，未找到匹配的用户");
                }
            } finally {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user1;
    }
}