// 添加必要的import和统计逻辑
package com.controller;

import com.entity.Admin;
import com.entity.User;
import com.service.Impl.UserServiceImpl;
import com.service.UserService;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AdminIndexServlet extends ViewBaseServlet {

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

        // 查询统计数据
        List<User> users = userService.getAllUsers();
        int totalUsers = users != null ? users.size() : 0;
        ctx.setVariable("totalUsers", totalUsers);

        // 其他统计数据暂时设为0（需要对应服务支持）
        ctx.setVariable("todaySignIn", 0);
        ctx.setVariable("reservedSeats", 0);
        ctx.setVariable("pendingTasks", 0);

        getTemplateEngine().process("Admin/index", ctx, response.getWriter());
    }
}