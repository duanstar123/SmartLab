package com.dao;

import com.entity.User;

import java.util.List;

public interface UserDao {
    User findUserById(String studentId);
    int save(User user);
    int delete(String studentId);
    List<User> findAllUsers();
}