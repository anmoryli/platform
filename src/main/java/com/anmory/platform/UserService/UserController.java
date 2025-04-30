package com.anmory.platform.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * @description TODO
 * @date 2025-03-24 下午12:39
 */

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserUploadService userUploadService;
    @Autowired
    UserImageRecognitionService userImageRecognitionService;

    @Autowired
    UserService userService;
    @RequestMapping("/login")
    public boolean login(String username, String password, HttpSession session) {
        if(!StringUtils.hasLength(username)) {
            return false;
        }
        User user = userService.getByName(username);
        if(user.getPassword().equals(password)) {
            session.setAttribute("session_user_key",user);
            session.setMaxInactiveInterval(10 * 365 * 24 * 60 * 60);
            return true;
        }
        return false;
    }

    @RequestMapping("/confirmLogin")
    public boolean confirmLogin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("session_user_key");
        System.out.println(user);
        return user != null;
    }

    @RequestMapping("/adminLogin")
    public User adminLogin(String username, String password, HttpSession session) {
        if(!StringUtils.hasLength(username)) {
            return null;
        }
        User user = userService.getByName(username);
        if(user.getPassword().equals(password)) {
            session.setAttribute("session_user_key",user);
            return user;
        }
        return null;
    }

    @RequestMapping("register")
    public int register(String username, String password) {
        if(!StringUtils.hasLength(username)) {
            return -1;
        }
        User user = new User();
        user = userService.getByName(username);
            return userService.addUser(username, password);
    }

    @RequestMapping("bindEmail")
    public boolean bindEmail(String username, String email) {
        System.out.println("bindEmail:" + username + email);
        if(!StringUtils.hasLength(username)) {
            return false;
        }
        User user = new User();
        String isBindEmail = userService.getEmailByName(username);
        if(isBindEmail == null) {
            userService.bindEmail(username,email);
            return true;
        }
        else{
            return true;
        }
    }

    String authCode = "";
    @RequestMapping("/getCode")
    public boolean findPassword(String email, HttpServletRequest request) {
        // 从session中获取用户信息
        HttpSession session = request.getSession();
        System.out.println("session:" + session);
        User user = (User) session.getAttribute("session_user_key");
        String username = user.getUsername();
        if(!StringUtils.hasLength(username)) {
            return false;
        }
        if(user.getEmail() == null) {
            return false;
        }
        // 邮件验证
        authCode = String.valueOf(new Random().nextInt(899999) + 100000);
        EmailConfig.sendEmailCode(email,authCode);
        return true;
    }

    @RequestMapping("/vertifyCode")
    public boolean verifyCode(HttpServletRequest request, String code) {
        return Objects.equals(code, authCode);
    }

    @RequestMapping("/changePassword")
    public int changePassword(String password, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("session_user_key");
        return userService.changePassword(user.getUsername(),password);
    }

    @RequestMapping("/getUserAndContribution")
    public User getUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("session_user_key");
        int contribution = userService.getContribution(user.getUsername());
        user.setRecordNums(contribution);
        session.setAttribute("session_user_key",user);
        return user;
    }

    @RequestMapping("/checkSession")
    public boolean checkSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("session_user_key");
        return user != null;
    }

    @RequestMapping("/adminPermession")
    public boolean adminPermession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("session_user_key");
        return user != null;
    }

    @RequestMapping("/isAdmin")
    public boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("session_user_key");
        return user.getIsAdmin() == 1;
    }

    @RequestMapping("/isPassedAmin")
    public boolean isPassedAmin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("session_user_key");
        return user.getIsAdmin() == 1;
    }

    @PostMapping("/upload")
    public int upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("session_user_key");

        // 验证用户登录
        if (user == null) {
            System.out.println("[Upload] User not logged in");
            return -1;
        }

        // 验证文件
        if (file == null || file.isEmpty()) {
            System.out.println("[Upload] File is empty or null");
            return -1;
        }

        int fileSize = (int) file.getSize();
        String originalFilename = file.getOriginalFilename();
        String uploadDirPath = "/usr/local/nginx/images/upload/";
        String uploadPath = uploadDirPath + originalFilename;

        System.out.println("[Upload] Target path: " + uploadPath);

        try {
            // 检查并创建目录
            File dir = new File(uploadDirPath);
            if (!dir.exists()) {
                System.out.println("[Upload] Creating directory: " + uploadDirPath);
                boolean created = dir.mkdirs();
                if (!created) {
                    System.out.println("[Upload] Failed to create directory: " + uploadDirPath);
                    return -1;
                }
            }

            // 检查目录写权限
            if (!dir.canWrite()) {
                System.out.println("[Upload] Directory is not writable: " + uploadDirPath);
                return -1;
            }

            // 检查文件是否已存在并尝试删除
            File targetFile = new File(uploadPath);
            if (targetFile.exists()) {
                System.out.println("[Upload] File exists, attempting to delete: " + uploadPath);
                boolean deleted = targetFile.delete();
                if (!deleted) {
                    System.out.println("[Upload] Failed to delete existing file: " + uploadPath);
                    return -1;
                }
            }

            // 写入文件
            System.out.println("[Upload] Writing file: " + uploadPath);
            try (FileOutputStream fos = new FileOutputStream(targetFile)) {
                fos.write(file.getBytes());
                fos.flush();
            }

            // 验证文件是否成功写入
            if (!targetFile.exists() || targetFile.length() != fileSize) {
                System.out.println("[Upload] File write verification failed: " + uploadPath);
                return -1;
            }

            System.out.println("[Upload] File uploaded successfully: " + uploadPath);

            // 插入数据库记录
            int uploadId = userUploadService.insert(
                    user.getId(),
                    originalFilename,
                    uploadPath,
                    "图片",
                    fileSize,
                    null,
                    false
            );

            if (uploadId <= 0) {
                System.out.println("[Upload] Failed to insert upload record to database");
                targetFile.delete(); // 清理无效文件
                return -1;
            }

            return uploadId;

        } catch (IOException e) {
            System.out.println("[Upload] IOException during file upload: " + e.getMessage());
            return -1;
        } catch (Exception e) {
            System.out.println("[Upload] Unexpected error during upload: " + e.getMessage());
            return -1;
        }
    }

    @RequestMapping("/getUserUpload")
    public List<UserUpload> getUserUpload(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("session_user_key");
        if(user == null) {
            System.out.println("用户未登录");
            return null;
        }
        return userUploadService.selectAll();
    }

    @RequestMapping("/getRecognitionHistory")
    public List<UserImageRecognition> getRecognitionHistory(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("session_user_key");
        if(user == null) {
            System.out.println("用户未登录");
            return null;
        }
        return userImageRecognitionService.selectAll();
    }
}
