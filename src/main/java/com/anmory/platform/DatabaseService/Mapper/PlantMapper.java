package com.anmory.platform.DatabaseService.Mapper;

import com.anmory.platform.DatabaseService.Dao.Plant;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-04-23 下午2:26
 */

@Mapper
public interface PlantMapper {
    @Insert("insert into plant (medicine.plant.plant_name, medicine.plant.plant_latin, medicine.plant.plant_alias, medicine.plant.plant_tibetan_name, medicine.plant.plant_family_name, medicine.plant.plant_genus_name, img_path, medicine.plant.plant_features, medicine.plant.plant_origin, medicine.plant.plant_protect_level, medicine.plant.notes) values" +
            "(#{plantName},#{plantLatin},#{plantAlias},#{plantTibetanName},#{plantFamilyName},#{plantGenusName},#{imgPath},#{plantFeatures},#{plantOrigin},#{plantProtectLevel},#{notes}) ")
    int insertPlant(String plantName, String plantLatin, String plantAlias, String plantTibetanName, String plantFamilyName, String plantGenusName,String imgPath, String plantFeatures, String plantOrigin, String plantProtectLevel, String notes);

    @Select("select * from medicine.plant")
    List<Plant> selectAll();
}
