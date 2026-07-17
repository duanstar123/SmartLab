package com.controller;

import com.entity.Admin;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminIndexServlet extends ViewBaseServlet {
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

        getTemplateEngine().process("Admin/index", ctx, response.getWriter());
    }
}