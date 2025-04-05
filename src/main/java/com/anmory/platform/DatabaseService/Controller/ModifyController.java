package com.anmory.platform.DatabaseService.Controller;

import jakarta.servlet.http.HttpServletResponse;
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
 * @date 2025-02-25 上午5:39
 */

@RestController
public class ModifyController {
    private final DataSource dataSource;

    public ModifyController(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @RequestMapping("/modify")
    public Boolean modify(HttpServletResponse response,String name, String category, String score, String distribution, String description, String usage) throws SQLException {
        System.out.println(name);
        System.out.println(category);
        System.out.println(distribution);
        System.out.println(description);
        System.out.println(score);
        System.out.println(usage);
        // 根据name查找数据是否存在
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM `medicinalplants` WHERE `name`=?")) {
            pstmt.setString(1, name);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (!rs.next()) {
                    return false; // 数据不存在，修改失败
                } else {
                    response.setContentType("application/json");
                    // 数据存在，执行更新操作
                    try (PreparedStatement pstmt2 = conn.prepareStatement("UPDATE `medicinalplants` SET `category`=?, `efficacyScore`=?, `distribution`=?, `description`=?, `usage`=? WHERE `name`=?")) {
                        pstmt2.setString(1, category);
                        pstmt2.setString(2, score);
                        pstmt2.setString(3, distribution);
                        pstmt2.setString(4, description);
                        pstmt2.setString(5, usage);
                        pstmt2.setString(6, name);
                        pstmt2.executeUpdate(); // 执行更新
                    }
                    return true;
                }
            }
        }
    }
}
