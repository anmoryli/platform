package com.anmory.platform.DatabaseService.Controller;

import com.anmory.platform.DatabaseService.Dao.Herb;
import com.anmory.platform.DatabaseService.Service.MedicineService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-02-25 上午3:53
 */

@RestController
public class MedicineController {
    private final DataSource dataSource;
    public MedicineController(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @RequestMapping(value = "/database", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getList(HttpServletResponse response) {
        MedicineService medicineService = new MedicineService(dataSource);
        response.setContentType("application/json");
        try {
            return medicineService.data();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    @RequestMapping(value = "/herb", produces = MediaType.APPLICATION_JSON_VALUE)
    public String herb(HttpServletResponse response) {
        MedicineService medicineService = new MedicineService(dataSource);
        response.setContentType("application/json");
        try {
            return medicineService.herb();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    @RequestMapping(value = "/plant", produces = MediaType.APPLICATION_JSON_VALUE)
    public String plant(HttpServletResponse response) {
        MedicineService medicineService = new MedicineService(dataSource);
        response.setContentType("application/json");
        try {
            return medicineService.plant();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    @RequestMapping(value = "/pic", produces = MediaType.APPLICATION_JSON_VALUE)
    public String pic(HttpServletResponse response) {
        MedicineService medicineService = new MedicineService(dataSource);
        response.setContentType("application/json");
        try {
            return medicineService.pic();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

    @RequestMapping("/plantDetail")
    public Herb detail(String name) throws SQLException {
        MedicineService medicineService = new MedicineService(dataSource);
        System.out.println("植物名称:" + name);
        return medicineService.detail(name);
    }
}