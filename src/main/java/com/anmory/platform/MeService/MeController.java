package com.anmory.platform.MeService;

import com.anmory.platform.LoginService.Controller.LoginController;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-02-25 下午12:50
 */

@RestController
public class MeController {
    private final DataSource dataSource;
    private final HttpSession session;
    public MeController(DataSource dataSource, HttpSession session) {
        this.dataSource = dataSource;
        this.session = session;
    }

    @RequestMapping("/me")
    public String me() {
        String name = (String)session.getAttribute("username");
        return name;
    }
}
