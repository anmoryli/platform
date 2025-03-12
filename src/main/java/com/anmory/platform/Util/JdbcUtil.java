package com.anmory.platform.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-02-25 上午3:13
 */

// 在这里配置数据库信息
public class JdbcUtil {
    // 数据库连接信息
    private static final String URL = "jdbc:mysql://localhost:3306/medicine";
    private static final String USER = "root";
    private static final String PASSWORD = "lmjnb666";

    // 获取数据库连接
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
