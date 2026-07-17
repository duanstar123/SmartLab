//package com.service.Impl;
//
//import com.dao.Impl.SignInDaoImpl;
//import com.dao.SignInDao;
//import com.entity.SignIn;
//import com.service.SignInService;
//
//import java.util.Date;
//import java.util.List;
//
//public class SignInServiceImpl implements SignInService {
//    // 实验室坐标（模拟）
//    private static final double LAB_LAT = 38.013794;
//    private static final double LAB_LNG = 112.446487;
//    // 签到范围（500米）
//    private static final double SIGN_IN_RANGE = 500;
//
//    @Override
//    public boolean signIn(String studentId) {
//        return signIn(studentId, null, null);
//    }
//
//    @Override
//    public boolean signIn(String studentId, Double latitude, Double longitude) {
//        try {
//            SignInDao signInDao = new SignInDaoImpl();
//            // 检查今天是否已签到
//            SignIn existingSignIn = signInDao.findByDate(studentId, new Date());
//            if (existingSignIn != null) {
//                return false; // 已签到
//            }
//
//            // 创建新签到记录
//            SignIn signIn = new SignIn();
//            signIn.setStudentId(studentId);
//            signIn.setSignInTime(new Date());
//            signIn.setLatitude(latitude);
//            signIn.setLongitude(longitude);
//
//            // 判断签到状态（假设8:30前为正常）
//            Date now = new Date();
//            String status = "正常";
//            if (now.getHours() >= 8 && now.getMinutes() > 30) {
//                status = "迟到";
//            }
//
//            // 判断是否在签到范围内
//            if (latitude != null && longitude != null) {
//                double distance = calculateDistance(latitude, longitude, LAB_LAT, LAB_LNG);
//                if (distance > SIGN_IN_RANGE) {
//                    status = "不在签到范围内";
//                }
//            }
//
//            signIn.setStatus(status);
//
//            int result = signInDao.insertSignIn(signIn);
//            return result > 0;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    // 计算两点间距离（米）
//    private double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
//        double R = 6371000;
//        double dLat = (lat2 - lat1) * Math.PI / 180;
//        double dLng = (lng2 - lng1) * Math.PI / 180;
//        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
//                Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) *
//                        Math.sin(dLng/2) * Math.sin(dLng/2);
//        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
//        return R * c;
//    }
//
//    @Override
//    public List<SignIn> getSignInRecords(String studentId) {
//        try {
//            SignInDao signInDao = new SignInDaoImpl();
//            return signInDao.findByStudentId(studentId);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Override
//    public boolean signOut(String studentId) {
//        try {
//            SignInDao signInDao = new SignInDaoImpl();
//            // 检查今天是否已签到但未签退
//            SignIn existingSignIn = signInDao.findByDate(studentId, new Date());
//            if (existingSignIn == null || existingSignIn.getSignOutTime() != null) {
//                return false; // 未签到或已签退
//            }
//
//            int result = signInDao.signOut(studentId, new Date());
//            return result > 0;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//}
package com.service.Impl;

import com.dao.Impl.SignInDaoImpl;
import com.dao.SignInDao;
import com.entity.SignIn;
import com.service.SignInService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SignInServiceImpl implements SignInService {
    private static final double LAB_LAT = 37.9047;   // 中北大学工程训练中心纬度
    private static final double LAB_LNG = 112.5667;  // 中北大学工程训练中心经度
    // 签到范围（500米）
    private static final double SIGN_IN_RANGE = 500;

    @Override
    public boolean signIn(String studentId) {
        return signIn(studentId, null, null);
    }

    @Override
    public boolean signIn(String studentId, Double latitude, Double longitude) {
        try {
            SignInDao signInDao = new SignInDaoImpl();
            // 查询今日签到记录
            SignIn todayRecord = signInDao.findByDate(studentId, new Date());
            // 【修复】今日有签到且尚未签退 → 禁止重复签到
            if (todayRecord != null && todayRecord.getSignOutTime() == null) {
                return false;
            }

            // 距离校验
            boolean inRange = true;
            if (latitude != null && longitude != null) {
                double distance = calculateDistance(latitude, longitude, LAB_LAT, LAB_LNG);
                if (distance > SIGN_IN_RANGE) {
                    inRange = false;
                }
            }

             if (!inRange) {
                 // 不在签到范围内，直接返回失败
                 return false;
             }

            SignIn signIn = new SignIn();
            signIn.setStudentId(studentId);
            signIn.setSignInTime(new Date());
            signIn.setLatitude(latitude);
            signIn.setLongitude(longitude);

            String status;
            if (!inRange) {
                status = "不在签到范围内";
            } else {
                status = "正常";
            }
            signIn.setStatus(status);
            int result = signInDao.insertSignIn(signIn);
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 计算两点间距离（米）
    private double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        double R = 6371000;
        double dLat = (lat2 - lat1) * Math.PI / 180;
        double dLng = (lng2 - lng1) * Math.PI / 180;
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }

    @Override
    public List<SignIn> getSignInRecords(String studentId) {
        try {
            SignInDao signInDao = new SignInDaoImpl();
            return signInDao.findByStudentId(studentId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean signOut(String studentId) {
        try {
            SignInDao signInDao = new SignInDaoImpl();
            SignIn todayRecord = signInDao.findByDate(studentId, new Date());
            // 今日无签到 或者 已经签退
            if (todayRecord == null || todayRecord.getSignOutTime() != null) {
                return false;
            }
            int result = signInDao.signOut(studentId, new Date());
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public SignIn findByDate(String studentId, Date date) {
        try {
            SignInDao signInDao = new SignInDaoImpl();
            return signInDao.findByDate(studentId, date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}