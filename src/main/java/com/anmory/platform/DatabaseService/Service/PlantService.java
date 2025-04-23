package com.anmory.platform.DatabaseService.Service;

import com.anmory.platform.DatabaseService.Dao.Plant;
import com.anmory.platform.DatabaseService.Mapper.PlantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-04-23 下午2:31
 */

@Service
public class PlantService {
    @Autowired
    PlantMapper plantMapper;

    public int insertPlant(String plantName,
                           String plantLatin,
                           String plantAlias,
                           String plantTibetanName,
                           String plantFamilyName,
                           String plantGenusName,
                           String imgPath,
                           String plantFeatures,
                           String plantOrigin,
                           String plantProtectLevel,
                           String notes) {
        return plantMapper.insertPlant(plantName,
                plantLatin,
                plantAlias,
                plantTibetanName,
                plantFamilyName,
                plantGenusName,
                imgPath,
                plantFeatures,
                plantOrigin,
                plantProtectLevel,
                notes);
    }

    public List<Plant> selectAll() {
        return plantMapper.selectAll();
    }
}
