package com.service;

import com.entity.User;

import java.util.List;

public interface UserService {
    User login(String studentId, String password);
    int regist(User user);
    User findUserById(String studentId);
    List<User> getAllUsers();
    int deleteUser(String studentId);
}