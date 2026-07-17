package com.controller;

import com.entity.User;
import com.service.Impl.UserServiceImpl;
import com.service.UserService;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserRegistServlet extends ViewBaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processTemplate("register_user", request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        try {
            User user = new User();
            user.setStudentId(request.getParameter("studentId"));
            user.setName(request.getParameter("name"));
            user.setGender(request.getParameter("gender"));
            user.setPassword(request.getParameter("password"));
            user.setGrade(request.getParameter("grade"));
            user.setCollegeName(request.getParameter("collegeName"));
            user.setDepartmentName(request.getParameter("departmentName"));
            user.setClassName(request.getParameter("className"));

            UserService userService = new UserServiceImpl();
            int rs = userService.regist(user);

            WebContext webContext = new WebContext(request, response, getServletContext());

            if (rs > 0) {
                response.sendRedirect(request.getContextPath() + "/login");
            } else {
                webContext.setVariable("error", "注册失败，学号已存在");
                processTemplate("register_user", request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            WebContext webContext = new WebContext(request, response, getServletContext());
            webContext.setVariable("error", "注册失败：" + e.getMessage());
            processTemplate("register_user", request, response);
        }
    }
}