package com.anmory.platform.DatabaseService.Controller;

import com.anmory.platform.DatabaseService.Service.MedicineService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

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
}
