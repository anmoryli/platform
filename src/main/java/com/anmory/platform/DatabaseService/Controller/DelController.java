package com.anmory.platform.DatabaseService.Controller;

import com.anmory.platform.DatabaseService.Service.HerbServiceR;
import com.anmory.platform.DatabaseService.Service.LiteratureService;
import com.anmory.platform.DatabaseService.Service.PlantService;
import com.anmory.platform.DatabaseService.Service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-04-24 上午8:12
 */

@RestController
@RequestMapping("/del")
public class DelController {
    @Autowired
    HerbServiceR herbServiceR;
    @Autowired
    PlantService plantService;
    @Autowired
    LiteratureService literatureService;
    @Autowired
    PrescriptionService prescriptionService;

    @RequestMapping("/herb")
    public int deleteHerb(@RequestParam int id) {
        return herbServiceR.deleteHerb(id);
    }

    @RequestMapping("/plant")
    public int deletePlant(@RequestParam int id) {
        int i = plantService.deletePlant(id);
        if(i != 1) {
            return 0;
        }
        else if(i == 1) return 1;
        return -1;
    }

    @RequestMapping("/literature")
    public int deleteLiterature(@RequestParam int id) {
        return literatureService.deleteLiterature(id);
    }

    @RequestMapping("/prescription")
    public int deletePrescription(@RequestParam int id) {
        return prescriptionService.deletePre(id);
    }
}
