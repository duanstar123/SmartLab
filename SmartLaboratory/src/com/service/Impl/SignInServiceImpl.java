package com.service.Impl;

import com.dao.Impl.SignInDaoImpl;
import com.dao.SignInDao;
import com.entity.SignIn;
import com.service.SignInService;

import java.util.Date;
import java.util.List;

public class SignInServiceImpl implements SignInService {
    @Override
    public boolean signIn(String studentId) {
        try {
            SignInDao signInDao = new SignInDaoImpl();
            // 检查今天是否已签到
            SignIn existingSignIn = signInDao.findByDate(studentId, new Date());
            if (existingSignIn != null) {
                return false; // 已签到
            }

            // 创建新签到记录
            SignIn signIn = new SignIn();
            signIn.setStudentId(studentId);
            signIn.setSignInTime(new Date());

            // 判断签到状态（假设8:30前为正常）
            Date now = new Date();
            if (now.getHours() < 8 || (now.getHours() == 8 && now.getMinutes() <= 30)) {
                signIn.setStatus("正常");
            } else {
                signIn.setStatus("迟到");
            }

            int result = signInDao.insertSignIn(signIn);
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<SignIn> getSignInRecords(String studentId) {
        try {
            SignInDao signInDao = new SignInDaoImpl();
            return signInDao.findByStudentId(studentId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}