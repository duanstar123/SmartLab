package com.controller;

import com.entity.User;
import com.service.Impl.UserServiceImpl;
import com.service.UserService;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserLoginServlet extends ViewBaseServlet {

    private UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        WebContext ctx = new WebContext(request, response, getServletContext());
        getTemplateEngine().process("User/login", ctx, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String studentId = request.getParameter("studentId");
        String password = request.getParameter("password");

        User user = userService.login(studentId, password);

        if (user != null) {
            request.getSession().setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/user/index");
        } else {
            WebContext ctx = new WebContext(request, response, getServletContext());
            ctx.setVariable("error", "学号或密码错误！");
            getTemplateEngine().process("User/login", ctx, response.getWriter());
        }
    }

}