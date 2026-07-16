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

        // 判断当前是否已签到
        SignIn todayRecord = records != null && !records.isEmpty() ? records.get(0) : null;
        boolean isSignedIn = todayRecord != null && todayRecord.getSignOutTime() == null;
        ctx.setVariable("isSignedIn", isSignedIn);
        ctx.setVariable("todayRecord", todayRecord);

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
        String action = request.getParameter("action");
        boolean success = false;
        String message = "";

        WebContext ctx = new WebContext(request, response, getServletContext());
        ctx.setVariable("user", user);

        if ("signout".equals(action)) {
            // 签退
            success = signInService.signOut(user.getStudentId());
            if (success) {
                // 计算学习时长
                List<SignIn> records = signInService.getSignInRecords(user.getStudentId());
                if (records != null && !records.isEmpty()) {
                    SignIn todayRecord = records.get(0);
                    if (todayRecord.getSignInTime() != null && todayRecord.getSignOutTime() != null) {
                        long duration = todayRecord.getSignOutTime().getTime() - todayRecord.getSignInTime().getTime();
                        long minutes = duration / (1000 * 60);
                        message = "签退成功，学习时长: " + minutes + "分钟";
                    } else {
                        message = "签退成功！";
                    }
                } else {
                    message = "签退成功！";
                }
            } else {
                message = "未签到或已签退！";
            }
        } else {
            // 签到 - 获取位置参数
            Double latitude = null;
            Double longitude = null;
            try {
                String latStr = request.getParameter("latitude");
                String lngStr = request.getParameter("longitude");
                if (latStr != null && !latStr.isEmpty()) {
                    latitude = Double.parseDouble(latStr);
                }
                if (lngStr != null && !lngStr.isEmpty()) {
                    longitude = Double.parseDouble(lngStr);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            success = signInService.signIn(user.getStudentId(), latitude, longitude);
            if (success) {
                message = "签到成功";
            } else {
                // 检查是否已签到
                List<SignIn> records = signInService.getSignInRecords(user.getStudentId());
                if (records != null && !records.isEmpty()) {
                    SignIn todayRecord = records.get(0);
                    if ("不在签到范围内".equals(todayRecord.getStatus())) {
                        message = "不在签到范围内，请前往指定地点签到！";
                    } else {
                        message = "今日已签到，请勿重复签到！";
                    }
                } else {
                    message = "签到失败，请重试！";
                }
            }
        }

        ctx.setVariable("message", message);

        // 获取更新后的签到记录
        List<SignIn> records = signInService.getSignInRecords(user.getStudentId());
        ctx.setVariable("signInRecords", records);

        // 判断当前是否已签到
        SignIn todayRecord = records != null && !records.isEmpty() ? records.get(0) : null;
        boolean isSignedIn = todayRecord != null && todayRecord.getSignOutTime() == null;
        ctx.setVariable("isSignedIn", isSignedIn);

        // 传递今日签到记录（用于显示签到时间）
        ctx.setVariable("todayRecord", todayRecord);

        processTemplate("User/signin", request, response);
    }
}