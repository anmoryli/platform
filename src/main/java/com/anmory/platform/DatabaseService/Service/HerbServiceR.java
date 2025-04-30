package com.anmory.platform.DatabaseService.Service;

import com.anmory.platform.DatabaseService.Dao.Herb;
import com.anmory.platform.DatabaseService.Mapper.HerbMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description TODO
 * @date 2025-04-23 下午8:44
 */

@Service
public class HerbServiceR {
    @Autowired
    HerbMapper herbMapper;

    public int insertHerb(
            String herbName,
            int plantId,
            String herbTibetanName,
            String herbAlias,
            String herbDescription,
            String partUsed,
            String herbFeatures,
            String flavorTropism,
            String functionIndication,
            String clinicalApplication,
            String pharmacologicalEffect,
            String notes,
            String recordedBy
    ) {
        return herbMapper.insertHerb(herbName, plantId, herbTibetanName, herbAlias, herbDescription, partUsed, herbFeatures, flavorTropism, functionIndication, clinicalApplication, pharmacologicalEffect, notes, recordedBy);
    }

    public List<Herb> selectAll() {
        return herbMapper.selectAll();
    }

    public Herb selectHerbById(int id) {
        return herbMapper.selectHerbById(id);
    }

    public Herb getHerbByName(String herbName) {
        return herbMapper.selectHerbByName(herbName);
    }

    public Integer getPlantIdByPlantName(String plantName) {
        return herbMapper.getPlantIdByPlantName(plantName);
    }

    public int deleteHerb(int id) {
        return herbMapper.deleteHerb(id);
    }

    public List<Herb> selectHerbPage(int offset, int size) {
        return herbMapper.selectHerbPage(offset, size);
    }
}
