package com.dao;

import com.entity.SignIn;

import java.util.Date;
import java.util.List;

public interface SignInDao {
    /**
     * 添加签到记录
     * @param signIn 签到对象
     * @return 影响的行数
     */
    public int insertSignIn(SignIn signIn);

    /**
     * 根据学号查询签到记录
     * @param studentId 学生学号
     * @return 签到记录列表
     */
    public List<SignIn> findByStudentId(String studentId);

    /**
     * 查询某天的签到记录
     * @param studentId 学生学号
     * @param date 日期
     * @return 签到记录
     */
    public SignIn findByDate(String studentId, Date date);
}