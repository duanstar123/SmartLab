package com.service;

import com.entity.SignIn;

import java.util.List;

public interface SignInService {
    /**
     * 用户签到
     * @param studentId 学生学号
     * @return 是否签到成功
     */
    public boolean signIn(String studentId);

    /**
     * 获取用户签到记录
     * @param studentId 学生学号
     * @return 签到记录列表
     */
    public List<SignIn> getSignInRecords(String studentId);
}