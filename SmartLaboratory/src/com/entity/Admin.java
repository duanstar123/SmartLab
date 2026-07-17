package com.entity;

public class Admin {
    private String adminId;  // 管理员ID
    private String username; // 管理员姓名
    private String password; // 密码
    private String phone;    // 联系电话
    private String email;    // 邮箱

    public Admin() {
    }

    public Admin(String adminId, String username, String password, String phone, String email) {
        this.adminId = adminId;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
    }

    // Getter and Setter methods
    public String getAdminId() {
        return adminId;
    }
    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}