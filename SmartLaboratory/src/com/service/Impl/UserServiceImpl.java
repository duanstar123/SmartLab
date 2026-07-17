package com.service.Impl;

import com.dao.UserDao;
import com.dao.Impl.UserDaoImpl;
import com.entity.User;
import com.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    @Override
    public User login(String studentId, String password) {
        User user = null;
        try {
            UserDao userDao = new UserDaoImpl();
            user = userDao.findUserById(studentId);
            if (user != null && !user.getPassword().equals(password)) {
                user = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public int regist(User user) {
        int result = 0;
        try {
            UserDao userDao = new UserDaoImpl();
            User existingUser = userDao.findUserById(user.getStudentId());
            if (existingUser == null) {
                result = userDao.save(user);
                System.out.println("注册成功: " + user.getStudentId() + " - " + user.getName());
            } else {
                System.out.println("用户已存在: " + user.getStudentId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public User findUserById(String studentId) {
        User user = null;
        try {
            UserDao userDao = new UserDaoImpl();
            user = userDao.findUserById(studentId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            UserDao userDao = new UserDaoImpl();
            users = userDao.findAllUsers();
            if (users == null) {
                users = new ArrayList<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public int deleteUser(String studentId) {
        int result = 0;
        try {
            UserDao userDao = new UserDaoImpl();
            result = userDao.delete(studentId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}