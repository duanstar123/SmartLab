package com.controller;

import com.entity.Admin;
import com.entity.SignIn;
import com.entity.User;
import com.service.Impl.SignInServiceImpl;
import com.service.Impl.UserServiceImpl;
import com.service.SignInService;
import com.service.UserService;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AdminSignInServlet extends ViewBaseServlet {

    private SignInService signInService = new SignInServiceImpl();
    private UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Admin admin = (Admin) request.getSession().getAttribute("admin");

        if (admin == null) {
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return;
        }

        response.setContentType("text/html;charset=UTF-8");
        WebContext ctx = new WebContext(request, response, getServletContext());
        ctx.setVariable("admin", admin);

        // 获取查询参数
        String dateStr = request.getParameter("date");
        Date queryDate = new Date(); // 默认今天

        if (dateStr != null && !dateStr.isEmpty()) {
            try {
                queryDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // 查询签到记录
        List<SignIn> signInRecords = signInService.getSignInRecordsByDate(queryDate);
        ctx.setVariable("signInRecords", signInRecords);
        ctx.setVariable("queryDate", new SimpleDateFormat("yyyy-MM-dd").format(queryDate));

        // 获取所有用户用于显示姓名
        List<User> users = userService.getAllUsers();
        ctx.setVariable("users", users);

        getTemplateEngine().process("Admin/signin", ctx, response.getWriter());
    }
}