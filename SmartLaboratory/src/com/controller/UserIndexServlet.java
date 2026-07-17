package com.controller;
import com.entity.User;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserIndexServlet extends HttpServlet {
    private TemplateEngine templateEngine;

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = this.getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        String viewPrefix = servletContext.getInitParameter("view-prefix");
        templateResolver.setPrefix(viewPrefix);
        String viewSuffix = servletContext.getInitParameter("view-suffix");
        templateResolver.setSuffix(viewSuffix);
        templateResolver.setCacheTTLMs(60000L);
        templateResolver.setCacheable(true);
        templateResolver.setCharacterEncoding("utf-8");
        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取登录用户信息
        User user = (User) request.getSession().getAttribute("user");

        // 如果用户未登录，重定向到登录页面
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/user/login");
            return;
        }

        // 设置响应内容类型
        response.setContentType("text/html;charset=UTF-8");

        // 创建WebContext并设置变量
        WebContext ctx = new WebContext(request, response, getServletContext());
        ctx.setVariable("user", user);

        // 直接使用templateEngine处理模板
        templateEngine.process("User/index", ctx, response.getWriter());
    }
}