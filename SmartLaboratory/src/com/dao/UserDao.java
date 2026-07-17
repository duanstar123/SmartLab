package com.dao;

import com.entity.User;

import java.util.List;

public interface UserDao {
    /**
     * 根据用户名查询用户
     * @param user 包含用户名的对象
     * @return 用户对象
     */
    public User findByUsername(User user);
    /**
     * 插入用户记录
     * @param user 用户对象
     * @return 影响的行数
     */
    public int insertUSer(User user);
    /**
     * 用户登录验证
     * @param user 包含用户名和密码的对象
     * @return 用户对象
     */
    public User loginUser(User user);
}