package com.anmory.platform.DatabaseService.Service;

import com.anmory.platform.DatabaseService.Dao.Medicine;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-02-25 上午3:56
 */

public class MedicineService {
    private final DataSource dataSource;
    public MedicineService(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    public String data() throws JsonProcessingException {
        List<Medicine> lists = new ArrayList<Medicine>();
        // 获取medicinalplants表中数据
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM `medicinalplants`")) {
            try (java.sql.ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Medicine medicine = new Medicine(
                            rs.getString("name"),
                            rs.getString("category"),
                            rs.getString("efficacyScore"),
                            rs.getString("distribution"),
                            rs.getString("description"),
                            rs.getString("usage")
                    );
                    lists.add(medicine);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(lists);
        return json;
    }
}
