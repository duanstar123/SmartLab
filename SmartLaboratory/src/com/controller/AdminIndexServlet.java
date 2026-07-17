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
        // 获取登录管理员信息
        Admin admin = (Admin) request.getSession().getAttribute("admin");

        // 如果管理员未登录，重定向到登录页面
        if (admin == null) {
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return;
        }

        // 设置响应内容类型
        response.setContentType("text/html;charset=UTF-8");

        // 创建WebContext并设置变量
        WebContext ctx = new WebContext(request, response, getServletContext());
        ctx.setVariable("admin", admin);

        // 手动处理模板（不调用父类方法，避免重复创建WebContext）
        getTemplateEngine().process("Admin/index", ctx, response.getWriter());
    }

    // 获取模板引擎
    private org.thymeleaf.TemplateEngine getTemplateEngine() {
        // 由于ViewBaseServlet的templateEngine是private的，我们需要重新初始化
        org.thymeleaf.templateresolver.ServletContextTemplateResolver templateResolver =
                new org.thymeleaf.templateresolver.ServletContextTemplateResolver(getServletContext());
        templateResolver.setTemplateMode(org.thymeleaf.templatemode.TemplateMode.HTML);
        templateResolver.setPrefix(getServletContext().getInitParameter("view-prefix"));
        templateResolver.setSuffix(getServletContext().getInitParameter("view-suffix"));
        templateResolver.setCacheTTLMs(60000L);
        templateResolver.setCacheable(true);
        templateResolver.setCharacterEncoding("utf-8");

        org.thymeleaf.TemplateEngine templateEngine = new org.thymeleaf.TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        return templateEngine;
    }
}