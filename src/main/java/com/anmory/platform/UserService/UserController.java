package com.anmory.platform.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-03-24 下午12:39
 */

@RestController
@RequestMapping("/user")
public class UserController {

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
            return true;
        }
        return false;
    }

    @RequestMapping("register")
    public int register(String username, String password) {
        if(!StringUtils.hasLength(username)) {
            return -1;
        }
        User user = new User();
        user = userService.getByName(username);
        if(user != null) {
            return userService.addUser(username, password);
        }
        return -1;
    }

    @RequestMapping("bindEmail")
    public boolean bindEmail(String username, String email) {
        if(!StringUtils.hasLength(username)) {
            return false;
        }
        User user = new User();
        String isBindEmail = userService.getEmailByName(username);
        if(isBindEmail == null) {
            userService.bindEmail(username,email);
            return true;
        }
        return false;
    }

//    @PostMapping("/getCode")
//    @ResponseBody
//    public String mail(String targetEmail) {
//        // 随机生成六位数验证码
//        String authCode = String.valueOf(new Random().nextInt(899999) + 100000);
//        System.out.println("你的验证码为:" + authCode);
//        EmailConfig.sendEmailCode(targetEmail,authCode);
//        return "ok";
//    }

    String authCode = "";
    @RequestMapping("/getCode")
    public boolean findPassword(String email, HttpServletRequest request) {
        // 从session中获取用户信息
        HttpSession session = request.getSession();
        System.out.println("session:" + session);
        User user = (User) session.getAttribute("session_user_key");
        String username = user.getUserName();
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
        if(code == authCode) {
            return true;
        }
        return false;
    }

    @RequestMapping("/changePassword")
    public int changePassword(String password, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("session_user_key");
        return userService.changePassword(user.getUserName(),password);
    }
}
