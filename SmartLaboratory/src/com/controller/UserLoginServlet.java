package com.controller;

import com.entity.User;
import com.service.Impl.UserServiceImpl;
import com.service.UserService;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserLoginServlet extends ViewBaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processTemplate("login", request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        try {
            // 获取表单输入
            String userId = request.getParameter("userId");
            String password = request.getParameter("password");

            // 调试信息
            System.out.println("=== 登录请求 ===");
            System.out.println("学号: " + userId);
            System.out.println("密码: " + password);

            // 构建用户对象
            User user = new User();
            user.setStudentId(userId);
            user.setPassword(password);

            // 调用服务层进行数据库登录验证
            UserService userService = new UserServiceImpl();
            User user1 = userService.login(user);

            // 调试信息
            System.out.println("查询结果: " + (user1 != null ? "成功" : "失败"));
            if (user1 != null) {
                System.out.println("用户名: " + user1.getName());
                System.out.println("学号: " + user1.getStudentId());
            }

            // 验证登录结果
            if (user1 != null && user1.getStudentId() != null) {
                // 登录成功，将用户信息存入session
                request.getSession().setAttribute("user", user1);
                System.out.println("登录成功，重定向到首页");
                // 重定向到用户首页
                response.sendRedirect(request.getContextPath() + "/user/index");
            } else {
                // 登录失败，返回登录页面并显示错误信息
                WebContext ctx = new WebContext(request, response, getServletContext());
                ctx.setVariable("error", "登录失败，学号或密码错误");
                processTemplate("login", request, response);
            }
        } catch (Exception e) {
            // 处理异常情况
            e.printStackTrace();
            WebContext ctx = new WebContext(request, response, getServletContext());
            ctx.setVariable("error", "登录失败：" + e.getMessage());
            processTemplate("login", request, response);
        }
    }
}