package com.service.Impl;

import com.dao.Impl.UserDaoImpl;
import com.dao.UserDao;
import com.entity.User;
import com.service.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public int regist(User user) {
        int rs = 0;
        try {
            UserDao userDao = new UserDaoImpl();
            User user1 = userDao.findByUsername(user);  // 检查学号是否已存在
            if (user1 == null) {  // 学号不存在才能注册
                rs = userDao.insertUSer(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    @Override
    public User login(User user) {
        User user1 = null;
        try {
            UserDao userDao = new UserDaoImpl();
            user1 = userDao.loginUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user1;
    }
}
