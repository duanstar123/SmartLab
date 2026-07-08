package com.service;

import com.entity.Admin;

public interface AdminService {
    /**
     * 管理员注册
     * @param admin 管理员对象
     * @return 注册结果：0-失败（已存在），1-成功
     */
    public int regist(Admin admin);

    /**
     * 管理员登录
     * @param admin 包含管理员ID和密码的对象
     * @return 管理员对象
     */
    public Admin login(Admin admin);
}