package com.entity;

import java.util.Date;

public class SignIn {
    private int id;              // 签到记录ID
    private String studentId;    // 学生学号
    private String name;           // 学生姓名
    private Date signInTime;     // 签到时间
    private String status;       // 签到状态（正常/迟到/早退等）

    public SignIn() {
    }
    public SignIn(String studentId, String name, Date signInTime, String status) {
        this.studentId = studentId;
        this.name = name;
        this.signInTime = signInTime;
        this.status = status;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Date getSignInTime() {
        return signInTime;
    }

    public void setSignInTime(Date signInTime) {
        this.signInTime = signInTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}