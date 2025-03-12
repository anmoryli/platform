package com.anmory.platform.AddService.Controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-02-25 上午6:10
 */

@RestController
public class AddController {
    private final DataSource dataSource;
    public AddController(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @RequestMapping("/add")
    public String add(HttpServletResponse response, String name, String category, String score, String distribution, String description, String usage) throws SQLException {
        // 向数据库中添加信息,添加的是形参里面的信息
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO `medicinalplants` (`name`, `category`, `efficacyScore`, `distribution`, `description`, `usage`) VALUES (?, ?, ?, ?, ?, ?)")) {
            pstmt.setString(1, name);
            pstmt.setString(2, category);
            pstmt.setString(3, score);
            pstmt.setString(4, distribution);
            pstmt.setString(5, description);
            pstmt.setString(6, usage);
            return pstmt.executeUpdate() > 0 ? "添加成功" : "添加失败";
        }

    }
}
