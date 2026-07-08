package com.dao.Impl;

import com.dao.BaseDao;
import com.dao.SignInDao;
import com.entity.SignIn;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SignInDaoImpl implements SignInDao {
    @Override
    public int insertSignIn(SignIn signIn) {
        int rs = 0;
        try {
            BaseDao dao = new BaseDao();
            String sql = "insert into signin(student_id, sign_in_time, status) values(?, ?, ?)";
            rs = dao.update(sql,
                    signIn.getStudentId(),
                    new java.sql.Timestamp(signIn.getSignInTime().getTime()),
                    signIn.getStatus()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    @Override
    public List<SignIn> findByStudentId(String studentId) {
        List<SignIn> list = null;
        try {
            BaseDao dao = new BaseDao();
            String sql = "select * from signin where student_id=? order by sign_in_time desc";
            list = dao.getList(SignIn.class, sql, studentId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public SignIn findByDate(String studentId, Date date) {
        SignIn signIn = null;
        try {
            BaseDao dao = new BaseDao();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = sdf.format(date);
            String sql = "select * from sign_in where student_id=? and DATE(sign_in_time)=?";
            signIn = dao.getBean(SignIn.class, sql, studentId, dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return signIn;
    }
}