package com.entity;

public class User {
    private String studentId;      // 学生学号
    private String name;           // 学生姓名
    private String gender;         // 性别
    private String password;       // 密码
    private String grade;          // 年级
    private String collegeName;    // 学院名
    private String departmentName; // 系名
    private String className;      // 班级名
    public User() {
    }
    public User(String studentId, String name, String gender, String password, String collegeName, String grade, String departmentName, String className) {
        this.studentId = studentId;
        this.name = name;
        this.gender = gender;
        this.password = password;
        this.collegeName = collegeName;
        this.grade = grade;
        this.departmentName = departmentName;
        this.className = className;
    }

    public String getStudentId() {
        return studentId;
    }
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getGrade() {
        return grade;
    }
    public void setGrade(String grade) {
        this.grade = grade;
    }
    public String getCollegeName() {
        return collegeName;
    }
    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }
    public String getDepartmentName() {
        return departmentName;
    }
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }
}