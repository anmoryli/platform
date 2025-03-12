package com.anmory.platform.RegisterService.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@RestController
public class RegisterController {

    private final DataSource dataSource;

    public RegisterController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @RequestMapping("/register")
    public Boolean register(@RequestParam String username, @RequestParam String password) {
        // 先检查用户名是否已经存在
        String checkSql = "SELECT COUNT(*) FROM `user_info` WHERE `username` = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(checkSql)) {

            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    if (count > 0) {
                        return false; // 用户名已存在，注册失败
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String sql = "INSERT INTO `user_info` (`username`, `password`) VALUES (?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 设置参数
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            // 执行插入操作
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
