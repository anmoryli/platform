package com.anmory.platform.DatabaseService.Service;

import com.anmory.platform.DatabaseService.Dao.Herb;
import com.anmory.platform.DatabaseService.Dao.Medicine;
import com.anmory.platform.DatabaseService.Dao.Picture;
import com.anmory.platform.DatabaseService.Dao.Plant;
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

    public String herb() throws JsonProcessingException {
        List<Herb> lists = new ArrayList<>();
        // 获取medicinal plants表中数据
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM `herb`")) {
            try (java.sql.ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Herb herb = new Herb(
                            rs.getInt("herb_id"), // 修改为 herb_id
                            rs.getString("herb_name"), // 修改为 herb_name
                            rs.getString("herb_tibetan_name"), // 修改为 herb_tibetan_name
                            rs.getString("herb_alias"), // 修改为 herb_alias
                            rs.getString("herb_description"), // 修改为 herb_description
                            rs.getString("part_used"), // 修改为 part_used
                            rs.getString("herb_features"), // 修改为 herb_features
                            rs.getString("flavor_tropism"), // 修改为 flavor_tropism
                            rs.getString("function_indication"), // 修改为 function_indication
                            rs.getString("clinical_application"), // 修改为 clinical_application
                            rs.getString("pharmacological_effect"), // 修改为 pharmacological_effect
                            rs.getString("research"), // 修改为 research
                            rs.getString("notes") // 修改为 notes
                    );
                    lists.add(herb);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(lists);
        return json;
    }

    public String plant() throws JsonProcessingException {
        List<Plant> lists = new ArrayList<>();
        // 获取medicinal plants表中数据
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM `plant`")) {
            try (java.sql.ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Plant plant = new Plant(
                            rs.getInt("plant_id"), // 修改为 herb_id
                            rs.getString("plant_name"), // 修改为 herb_name
                            rs.getString("plant_latin"), // 修改为 herb_tibetan_name
                            rs.getString("plant_alias"), // 修改为 herb_alias
                            rs.getString("plant_tibetan_name"), // 修改为 herb_description
                            rs.getString("plant_family_name"), // 修改为 part_used
                            rs.getString("plant_genus_name"), // 修改为 herb_features
                            rs.getString("plant_features"), // 修改为 flavor_tropism
                            rs.getString("plant_origin"), // 修改为 function_indication
                            rs.getString("plant_protect_level"), // 修改为 clinical_application
                            rs.getString("notes") // 修改为 notes
                    );
                    lists.add(plant);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(lists);
        return json;
    }

    public String pic() throws JsonProcessingException {
        List<Picture> lists = new ArrayList<>();
        // 获取medicinal plants表中数据
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM `picture`")) {
            try (java.sql.ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Picture picture = new Picture(
                            rs.getInt("id"), // 修改为 herb_id
                            rs.getInt("plant_id"), // 修改为 herb_id
                            rs.getString("pic_name"), // 修改为 herb_name
                            rs.getString("pic_cate"), // 修改为 herb_tibetan_name
                            rs.getString("pic_part"), // 修改为 herb_alias
                            rs.getString("file_name"), // 修改为 herb_description
                            rs.getString("description"), // 修改为 part_used
                            rs.getString("pic_place"), // 修改为 herb_features
                            rs.getDate("pic_time"), // 修改为 flavor_tropism
                            rs.getString("pic_person")
                    );
                    lists.add(picture);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(lists);
        return json;
    }

    public Herb detail(String name) throws SQLException {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM `herb` WHERE `herb_name`= ?")) {
            pstmt.setString(1, name);
            try (java.sql.ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Herb(
                            rs.getInt("herb_id"), // 修改为 herb_id
                            rs.getString("herb_name"), // 修改为 herb_name
                            rs.getString("herb_tibetan_name"), // 修改为 herb_tibetan_name
                            rs.getString("herb_alias"), // 修改为 herb_alias
                            rs.getString("herb_description"), // 修改为 herb_description
                            rs.getString("part_used"), // 修改为 part_used
                            rs.getString("herb_features"),
                            rs.getString("flavor_tropism"),
                            rs.getString("function_indication"),
                            rs.getString("clinical_application"),
                            rs.getString("pharmacological_effect"),
                            rs.getString("research"),
                            rs.getString("notes")
                    );
                }
            }
        }
        return null;
    }
}
