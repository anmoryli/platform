package com.anmory.platform.BotService.Tools;

import com.anmory.platform.DatabaseService.Dao.Herb;
import com.anmory.platform.DatabaseService.Dao.Plant;
import com.anmory.platform.DatabaseService.Service.HerbServiceR;
import com.anmory.platform.DatabaseService.Service.LiteratureService;
import com.anmory.platform.DatabaseService.Service.PlantService;
import com.anmory.platform.DatabaseService.Service.PrescriptionService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @description TODO
 * @date 2025-04-24 下午3:48
 */

@Component
public class NL2SQL {
    @Autowired
    HerbServiceR herbServiceR;

    @Autowired
    PlantService plantService;

    @Autowired
    PrescriptionService prescriptionService;

    @Autowired
    LiteratureService literatureService;
    @Tool(description = "明天是几月几号?")
    String date() {
        System.out.println("NL2SQL我被调用辣");
        return LocalDateTime.now().plusDays(1).toString();
    }

    @Tool(name = "query_table_data_herb", description = "查询药材表的所有数据")
    List<Herb> select() {
        System.out.println("select");
        if(herbServiceR == null) return null;
        return herbServiceR.selectAll();
    }

    @Tool(name = "query_table_data_plant", description = "查询植物表的所有数据")
    List<Plant> selectPlant() {
        System.out.println("select");
        if(plantService == null) return null;
        return plantService.selectAll();
    }

    @Tool(name = "query_table_data_prescription", description = "查询处方表的所有数据")
    List<com.anmory.platform.DatabaseService.Dao.Prescription> selectPrescription() {
        System.out.println("select");
        if(prescriptionService == null) return null;
        return prescriptionService.selectAll();
    }

    @Tool(name = "query_table_data_literature", description = "查询文献表的所有数据")
    String selectLiterature() {
        System.out.println("select");
        if(literatureService == null) return null;
        return literatureService.selectAll().toString();
    }
}
