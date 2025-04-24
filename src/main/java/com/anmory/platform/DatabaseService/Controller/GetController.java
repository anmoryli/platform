package com.anmory.platform.DatabaseService.Controller;

import com.anmory.platform.DatabaseService.Dao.Literature;
import com.anmory.platform.DatabaseService.Dao.Plant;
import com.anmory.platform.DatabaseService.Dao.Prescription;
import com.anmory.platform.DatabaseService.Service.HerbServiceR;
import com.anmory.platform.DatabaseService.Service.LiteratureService;
import com.anmory.platform.DatabaseService.Service.PlantService;
import com.anmory.platform.DatabaseService.Service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-04-24 上午7:55
 */

@RestController
@RequestMapping("/db")
public class GetController {
    @Autowired
    HerbServiceR herbServiceR;
    @Autowired
    PlantService plantService;
    @Autowired
    LiteratureService literatureService;
    @Autowired
    PrescriptionService prescriptionService;

    @RequestMapping("/plant")
    public List<Plant> getPlants() {
        return plantService.selectAll();
    }

    @RequestMapping("/herb")
    public List<com.anmory.platform.DatabaseService.Dao.Herb> getHerbs() {
        return herbServiceR.selectAll();
    }

    @RequestMapping("/literature")
    public List<Literature> getLiterature() {
        return literatureService.selectAll();
    }

    @RequestMapping("/prescription")
    public List<Prescription> getPrescription() {
        return prescriptionService.selectAll();
    }

    @RequestMapping("/relation")
    public List<com.anmory.platform.DatabaseService.Dao.Relation> getRelation() {
        return plantService.selectRelation();
    }
}
