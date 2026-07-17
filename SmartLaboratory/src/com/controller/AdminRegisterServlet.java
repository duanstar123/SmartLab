package com.controller;

import com.entity.Admin;
import com.service.Impl.AdminServiceImpl;
import com.service.AdminService;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminRegisterServlet extends ViewBaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processTemplate("register_admin", request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        try {
            Admin admin = new Admin();
            admin.setAdminId(request.getParameter("adminId"));
            admin.setUsername(request.getParameter("username"));
            admin.setPassword(request.getParameter("password"));
            admin.setPhone(request.getParameter("phone"));
            admin.setEmail(request.getParameter("email"));

            AdminService adminService = new AdminServiceImpl();
            int rs = adminService.regist(admin);

            if (rs > 0) {
                response.sendRedirect(request.getContextPath() + "/login");
            } else {
                WebContext ctx = new WebContext(request, response, getServletContext());
                ctx.setVariable("error", "注册失败，管理员ID或用户名已存在");
                processTemplate("register_admin", request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            WebContext ctx = new WebContext(request, response, getServletContext());
            ctx.setVariable("error", "注册失败：" + e.getMessage());
            processTemplate("register_admin", request, response);
        }
    }
}