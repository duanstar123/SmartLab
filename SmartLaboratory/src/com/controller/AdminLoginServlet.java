package com.controller;

import com.entity.Admin;
import com.service.Impl.AdminServiceImpl;
import com.service.AdminService;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminLoginServlet extends ViewBaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processTemplate("login", request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        try {
            Admin admin = new Admin();
            admin.setAdminId(request.getParameter("teacherId"));
            admin.setPassword(request.getParameter("password"));

            AdminService adminService = new AdminServiceImpl();
            Admin admin1 = adminService.login(admin);

            if (admin1 != null && admin1.getAdminId() != null) {
                request.getSession().setAttribute("admin", admin1);
                response.sendRedirect(request.getContextPath() + "/admin/index");
            } else {
                WebContext ctx = new WebContext(request, response, getServletContext());
                ctx.setVariable("error", "登录失败，管理员ID或密码错误");
                processTemplate("login", request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            WebContext ctx = new WebContext(request, response, getServletContext());
            ctx.setVariable("error", "登录失败：" + e.getMessage());
            processTemplate("login", request, response);
        }
    }
}