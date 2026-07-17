package com.dao;

import com.entity.Admin;

public interface AdminDao {
    /**
     * 根据管理员ID查找管理员
     * @param admin 包含管理员ID的对象
     * @return 管理员对象
     */
    public Admin findByAdminId(Admin admin);

    /**
     * 根据用户名查找管理员
     * @param admin 包含用户名的对象
     * @return 管理员对象
     */
    public Admin findByUsername(Admin admin);

    /**
     * 插入管理员记录
     * @param admin 管理员对象
     * @return 影响的行数
     */
    public int insertAdmin(Admin admin);

    /**
     * 管理员登录验证
     * @param admin 包含管理员ID和密码的对象
     * @return 管理员对象
     */
    public Admin loginAdmin(Admin admin);
}