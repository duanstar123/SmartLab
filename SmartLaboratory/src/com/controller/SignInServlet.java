package com.controller;

import com.entity.SignIn;
import com.entity.User;
import com.service.Impl.SignInServiceImpl;
import com.service.SignInService;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class SignInServlet extends ViewBaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/user/login");
            return;
        }

        // 获取用户签到记录
        SignInService signInService = new SignInServiceImpl();
        List<SignIn> records = signInService.getSignInRecords(user.getStudentId());

        WebContext ctx = new WebContext(request, response, getServletContext());
        ctx.setVariable("signInRecords", records);
        ctx.setVariable("user", user);

        processTemplate("User/signin", request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/user/login");
            return;
        }

        SignInService signInService = new SignInServiceImpl();
        boolean success = signInService.signIn(user.getStudentId());

        WebContext ctx = new WebContext(request, response, getServletContext());
        ctx.setVariable("user", user);

        if (success) {
            ctx.setVariable("message", "签到成功！");
        } else {
            ctx.setVariable("message", "今日已签到，请勿重复签到！");
        }

        // 获取更新后的签到记录
        List<SignIn> records = signInService.getSignInRecords(user.getStudentId());
        ctx.setVariable("signInRecords", records);

        processTemplate("User/signin", request, response);
    }
}