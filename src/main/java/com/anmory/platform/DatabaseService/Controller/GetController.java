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

    @RequestMapping("/plantDetail")
    public Plant getPlantDetail(int id) {
        return plantService.selectPlantById(id);
    }

    @RequestMapping("/herbDetail")
    public com.anmory.platform.DatabaseService.Dao.Herb getHerbDetail(int id) {
        return herbServiceR.selectHerbById(id);
    }

    @RequestMapping("/literatureDetail")
    public Literature getLiteratureDetail(int id) {
        return literatureService.selectLiteratureById(id);
    }

    @RequestMapping("/prescriptionDetail")
    public Prescription getPrescriptionDetail(int id) {
        return prescriptionService.selectPrescriptionById(id);
    }

    @RequestMapping("/plantAll")
    public List<Plant> getPlants() {
        return plantService.selectAll();
    }

    @RequestMapping("/herbAll")
    public List<com.anmory.platform.DatabaseService.Dao.Herb> getHerbs() {
        return herbServiceR.selectAll();
    }

    @RequestMapping("/literatureAll")
    public List<Literature> getLiterature() {
        return literatureService.selectAll();
    }

    @RequestMapping("/prescriptionAll")
    public List<Prescription> getPrescription() {
        return prescriptionService.selectAll();
    }

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

    @RequestMapping("/searchPlant")
    public Plant searchPlant(String name) {
        return plantService.selectPlantByName(name);
    }

    @RequestMapping("/searchHerb")
    public com.anmory.platform.DatabaseService.Dao.Herb searchHerb(String name) {
        return herbServiceR.getHerbByName(name);
    }

    @RequestMapping("/searchLiterature")
    public Literature searchLiterature(String name) {
        return literatureService.getLiteratureByTitle(name);
    }

    @RequestMapping("/searchPrescription")
    public Prescription searchPrescription(String name) {
        return prescriptionService.getPrescriptionByName(name);
    }
}
