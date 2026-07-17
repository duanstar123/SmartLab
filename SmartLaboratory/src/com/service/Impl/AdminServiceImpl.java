package com.service.Impl;

import com.dao.Impl.AdminDaoImpl;
import com.dao.AdminDao;
import com.entity.Admin;
import com.service.AdminService;

public class AdminServiceImpl implements AdminService {
    @Override
    public int regist(Admin admin) {
        int rs = 0;
        try {
            AdminDao adminDao = new AdminDaoImpl();
            Admin admin1 = null;

            // 检查管理员ID是否已存在
            admin1 = adminDao.findByAdminId(admin);
            if (admin1 == null || admin1.getAdminId() == null) {
                // 检查用户名是否已存在
                admin1 = adminDao.findByUsername(admin);
                if (admin1 == null || admin1.getUsername() == null) {
                    rs = adminDao.insertAdmin(admin);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    @Override
    public Admin login(Admin admin) {
        Admin admin1 = null;
        try {
            AdminDao adminDao = new AdminDaoImpl();
            admin1 = adminDao.loginAdmin(admin);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return admin1;
    }
}