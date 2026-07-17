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
import java.util.ArrayList;
import java.util.List;

public class AdminUserServlet extends ViewBaseServlet {

    private UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        Admin admin = (Admin) request.getSession().getAttribute("admin");
        if (admin == null) {
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "add": showAddForm(request, response, admin); break;
            case "delete": deleteUser(request, response, admin); break;
            case "search": searchUser(request, response, admin); break;
            default: listUsers(request, response, admin); break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        Admin admin = (Admin) request.getSession().getAttribute("admin");
        if (admin == null) {
            response.sendRedirect(request.getContextPath() + "/admin/login");
            return;
        }

        String action = request.getParameter("action");
        if ("add".equals(action)) {
            addUser(request, response, admin);
        } else if ("delete".equals(action)) {
            deleteUser(request, response, admin);
        }
    }

    private void listUsers(HttpServletRequest request, HttpServletResponse response, Admin admin) throws IOException {
        List<User> users = userService.getAllUsers();
        if (users == null) users = new ArrayList<>();

        WebContext ctx = new WebContext(request, response, getServletContext());
        ctx.setVariable("admin", admin);
        ctx.setVariable("users", users);
        ctx.setVariable("searchKeyword", "");

        String message = request.getParameter("message");
        if ("addSuccess".equals(message)) {
            ctx.setVariable("message", "添加学生成功！");
            ctx.setVariable("messageType", "success");
        } else if ("addError".equals(message)) {
            ctx.setVariable("message", "添加学生失败，学号已存在！");
            ctx.setVariable("messageType", "error");
        }

        getTemplateEngine().process("Admin/users", ctx, response.getWriter());
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response, Admin admin) throws IOException {
        WebContext ctx = new WebContext(request, response, getServletContext());
        ctx.setVariable("admin", admin);
        ctx.setVariable("users", userService.getAllUsers());
        ctx.setVariable("searchKeyword", "");
        ctx.setVariable("showAddModal", true);
        getTemplateEngine().process("Admin/users", ctx, response.getWriter());
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response, Admin admin) throws IOException {
        String studentId = request.getParameter("studentId");
        String name = request.getParameter("name");
        String gender = request.getParameter("gender");
        String password = request.getParameter("password");
        String grade = request.getParameter("grade");
        String collegeName = request.getParameter("collegeName");
        String departmentName = request.getParameter("departmentName");
        String className = request.getParameter("className");

        User user = new User(studentId, name, gender, password, collegeName, grade, departmentName, className);
        int result = userService.regist(user);

        if (result > 0) {
            response.sendRedirect(request.getContextPath() + "/admin/users?message=addSuccess");
        } else {
            response.sendRedirect(request.getContextPath() + "/admin/users?message=addError");
        }
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response, Admin admin) throws IOException {
        String studentId = request.getParameter("studentId");
        if (studentId != null && !studentId.trim().isEmpty()) {
            userService.deleteUser(studentId);
        }
        response.sendRedirect(request.getContextPath() + "/admin/users");
    }

    private void searchUser(HttpServletRequest request, HttpServletResponse response, Admin admin) throws IOException {
        String keyword = request.getParameter("keyword") == null ? "" : request.getParameter("keyword");
        List<User> allUsers = userService.getAllUsers();
        if (allUsers == null) allUsers = new ArrayList<>();

        List<User> filteredUsers = new ArrayList<>();
        for (User user : allUsers) {
            String sid = user.getStudentId() != null ? user.getStudentId() : "";
            String uname = user.getName() != null ? user.getName() : "";
            if (sid.contains(keyword) || uname.contains(keyword)) {
                filteredUsers.add(user);
            }
        }

        WebContext ctx = new WebContext(request, response, getServletContext());
        ctx.setVariable("admin", admin);
        ctx.setVariable("users", filteredUsers);
        ctx.setVariable("searchKeyword", keyword);
        getTemplateEngine().process("Admin/users", ctx, response.getWriter());
    }
}