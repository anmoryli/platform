package com.anmory.platform.LoginService.Controller;
import com.anmory.platform.UserService.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-02-25 上午2:28
 */

@RestController
public class LoginController {
    private final DataSource dataSource;
    private final HttpSession session;
    public LoginController(DataSource dataSource, HttpSession session) {
        this.dataSource = dataSource;
        this.session = session;
    }
    String globalUsername = "";
    User user = new User();
    @RequestMapping("login")
    public Boolean login(String username, String password) throws SQLException {
        if(!StringUtils.hasLength(username) || !StringUtils.hasLength(password)) {
            return false;
        }
        // 从数据库检查是否存在用户
        String sql = "SELECT COUNT(*) FROM `user_info` WHERE `username` = ? AND `password` = ?";
        // 获取用户名和密码
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println(rs);
                    int count = rs.getInt(1);
                    if (count > 0) {
                        globalUsername = username;
                        session.setAttribute("username", username);
                        return true; // 用户名和密码匹配，登录成功
                    }
                }
            }
        }
        return false;
    }

    public String getName() {
        if(globalUsername != null) {
            return globalUsername;
        }
        return null;
    }
}
