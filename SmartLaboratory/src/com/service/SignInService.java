package com.service;

import com.entity.SignIn;

import java.util.Date;
import java.util.List;

public interface SignInService {
    /**
     * 用户签到（不带位置）
     * @param studentId 学生学号
     * @return 是否签到成功
     */
    public boolean signIn(String studentId);

    /**
     * 用户签到（带位置）
     * @param studentId 学生学号
     * @param latitude 纬度
     * @param longitude 经度
     * @return 是否签到成功
     */
    public boolean signIn(String studentId, Double latitude, Double longitude);

    /**
     * 获取用户签到记录
     * @param studentId 学生学号
     * @return 签到记录列表
     */
    public List<SignIn> getSignInRecords(String studentId);

    /**
     * 用户签退
     * @param studentId 学生学号
     * @return 是否签退成功
     */
    public boolean signOut(String studentId);

    SignIn findByDate(String studentId, Date date);
    /**
     * 获取所有签到记录
     * @return 签到记录列表
     */
    public List<SignIn> getAllSignInRecords();

    /**
     * 获取指定日期的签到记录
     * @param date 日期
     * @return 签到记录列表
     */
    public List<SignIn> getSignInRecordsByDate(Date date);

    /**
     * 统计某日签到人数
     * @param date 日期
     * @return 签到人数
     */
    public int countSignInByDate(Date date);
}