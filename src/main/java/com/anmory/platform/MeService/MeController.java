package com.anmory.platform.MeService;

import com.anmory.platform.LoginService.Controller.LoginController;
import com.anmory.platform.UserService.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

/**
 * @description TODO
 * @date 2025-02-25 下午12:50
 */

@RestController
public class MeController {
    private final DataSource dataSource;
    public MeController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @RequestMapping("/me")
    public User me(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("session_user_key");
        return user;
    }
}
