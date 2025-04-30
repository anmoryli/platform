package com.anmory.platform.PasswordService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @description TODO
 * @date 2025-03-24 上午8:13
 */

@RestController
public class PasswordController {

    private final DataSource dataSource;

    public PasswordController(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    @RequestMapping("/bindEmail")
    public Boolean bindEmail(String email, String password, HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
//        从数据库查询该用户是否存在
        Connection conn = dataSource.getConnection();
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM user_info WHERE username = ?");
//        存在则查询邮箱是否为空
        pstmt.setString(1, username);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            String email1 = rs.getString("email");
            if (email1 != null) {
                return false;
            }
            if (email1 == null) {
                String sql = "UPDATE user_info SET email = ? WHERE username = ?";
                try (PreparedStatement pstmt1 = conn.prepareStatement(sql)) {
                    pstmt1.setString(1, email);
                    pstmt1.setString(2, username);
                    pstmt1.executeUpdate();
                    return true;
                }
            }
        }
//        为空则绑定邮箱
        return false;
    }

    @RequestMapping("/findPassword")
    public String findPassword(String email,HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        Connection conn = dataSource.getConnection();
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM user_info WHERE username = ?");
//        存在则查询邮箱是否为空
        pstmt.setString(1, username);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            String email1 = rs.getString("email");
            if (email1 != null) {
                return "false";
            }
            if (email1 == null) {
                String sql = "UPDATE user_info SET email = ? WHERE username = ?";
                try (PreparedStatement pstmt1 = conn.prepareStatement(sql)) {
                    pstmt1.setString(1, email);
                    pstmt1.setString(2, username);
                    pstmt1.executeUpdate();
                    return "true";
                }
            }
        }
        return "false";
    }
}
