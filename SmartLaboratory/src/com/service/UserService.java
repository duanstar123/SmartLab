package com.service;

import com.entity.User;

public interface UserService {
    public int regist(User user);
    public User login(User user);
}
