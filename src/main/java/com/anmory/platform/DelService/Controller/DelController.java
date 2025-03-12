package com.anmory.platform.DelService.Controller;

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
 * @date 2025-02-25 上午7:58
 */

@RestController
public class DelController {
    private final DataSource dataSource;
    public DelController(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @RequestMapping("/del")
    public Boolean del(HttpServletResponse response, String name) throws SQLException {
        response.setContentType("application/json");
        // 根据name删除数据
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("DELETE FROM `medicinalplants` WHERE `name`=?")) {
            pstmt.setString(1, name);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                return true; // 删除成功
            } else {
                return false; // 删除失败
            }
        }
    }

}
