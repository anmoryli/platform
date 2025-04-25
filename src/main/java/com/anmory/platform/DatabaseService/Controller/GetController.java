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
    public List<Plant> getPlants(int page) {
        int offset = (page - 1) * 50;
        return plantService.selectPage(offset, 50);
    }

    @RequestMapping("/herb")
    public List<com.anmory.platform.DatabaseService.Dao.Herb> getHerbs(int page) {
        int offset = (page - 1) * 50;
        return herbServiceR.selectHerbPage(offset, 50);
    }

    @RequestMapping("/literature")
    public List<Literature> getLiterature(int page) {
        int offset = (page - 1) * 50;
        return literatureService.selectLiteraturePage(offset, 50);
    }

    @RequestMapping("/prescription")
    public List<Prescription> getPrescription(int page) {
        int offset = (page - 1) * 50;
        return prescriptionService.selectPrescriptionPage(offset, 50);
    }

    @RequestMapping("/relation")
    public List<com.anmory.platform.DatabaseService.Dao.Relation> getRelation(int page) {
        int offset = (page - 1) * 50;
        return plantService.selectRelationPage(offset, 50);
    }
}
