package com.dao.Impl;

import com.dao.JDBCTools;
import com.dao.SignInDao;
import com.entity.SignIn;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SignInDaoImpl implements SignInDao {
    @Override
    public int insertSignIn(SignIn signIn) {
        int rs = 0;
        Connection conn = null;
        try {
            conn = JDBCTools.getConnection();
            QueryRunner queryRunner = new QueryRunner();
            String sql = "insert into signin(student_id, sign_in_time, status, latitude, longitude) values(?, ?, ?, ?, ?)";
            rs = queryRunner.update(conn, sql,
                    signIn.getStudentId(),
                    new java.sql.Timestamp(signIn.getSignInTime().getTime()),
                    signIn.getStatus(),
                    signIn.getLatitude(),
                    signIn.getLongitude()
            );
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    JDBCTools.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return rs;
    }

    @Override
    public List<SignIn> findByStudentId(String studentId) {
        List<SignIn> list = null;
        Connection conn = null;
        try {
            conn = JDBCTools.getConnection();
            QueryRunner queryRunner = new QueryRunner();
            String sql = "select * from signin where student_id=? order by sign_in_time desc";
            list = queryRunner.query(conn, sql, new BeanListHandler<SignIn>(SignIn.class), studentId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    JDBCTools.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    @Override
    public SignIn findByDate(String studentId, Date date) {
        SignIn signIn = null;
        Connection conn = null;
        try {
            conn = JDBCTools.getConnection();
            QueryRunner queryRunner = new QueryRunner();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = sdf.format(date);
            String sql = "select * from signin where student_id=? and DATE(sign_in_time)=?";
            signIn = queryRunner.query(conn, sql, new BeanHandler<SignIn>(SignIn.class), studentId, dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    JDBCTools.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return signIn;
    }

    @Override
    public int signOut(String studentId, Date signOutTime) {
        int rs = 0;
        Connection conn = null;
        try {
            conn = JDBCTools.getConnection();
            QueryRunner queryRunner = new QueryRunner();
            String sql = "update signin set sign_out_time=? where student_id=? and sign_out_time is null";
            rs = queryRunner.update(conn, sql,
                    new java.sql.Timestamp(signOutTime.getTime()),
                    studentId
            );
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    JDBCTools.freeConnection(conn);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return rs;
    }
}