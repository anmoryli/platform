package com.anmory.platform.DatabaseService.Mapper;

import com.anmory.platform.DatabaseService.Dao.Plant;
import com.anmory.platform.DatabaseService.Dao.Relation;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @description TODO
 * @date 2025-04-23 下午2:26
 */

@Mapper
public interface PlantMapper {
    @Insert("insert into plant (medicine.plant.plant_name, medicine.plant.plant_latin, medicine.plant.plant_alias, medicine.plant.plant_tibetan_name, medicine.plant.plant_family_name, medicine.plant.plant_genus_name, img_path, medicine.plant.plant_features, medicine.plant.plant_origin, medicine.plant.plant_protect_level, medicine.plant.notes,medicine.plant.recorded_by) values" +
            "(#{plantName},#{plantLatin},#{plantAlias},#{plantTibetanName},#{plantFamilyName},#{plantGenusName},#{imgPath},#{plantFeatures},#{plantOrigin},#{plantProtectLevel},#{notes},#{recordedBy}) ")
    int insertPlant(String plantName, String plantLatin, String plantAlias, String plantTibetanName, String plantFamilyName, String plantGenusName,String imgPath, String plantFeatures, String plantOrigin, String plantProtectLevel, String notes, String recordedBy);

    @Select("select * from medicine.plant")
    List<Plant> selectAll();

    @Delete("delete from medicine.plant where medicine.plant.plant_id = #{id}")
    int deletePlant(int id);

    @Select("select medicine.plant.plant_id, medicine.plant.plant_name, medicine.herb.herb_name from medicine.plant, medicine.herb where medicine.plant.plant_id = medicine.herb.plant_id")
    List<Relation> selectRelation();

    @Select("select medicine.plant.plant_id, medicine.plant.plant_name, medicine.herb.herb_name from medicine.plant, medicine.herb where medicine.plant.plant_id = medicine.herb.plant_id order by plant_id desc limit #{offset}, #{size}")
    List<Relation> selectRelationPage(int offset, int size);

    @Select("select * from medicine.plant order by plant_id desc limit #{offset}, #{size}")
    List<Plant> selectPage(int offset, int size);

    @Select("select * from medicine.plant where medicine.plant.plant_name = #{name} limit 1")
    Plant getPlantByName(String name);

    @Select("select * from medicine.plant where medicine.plant.plant_id = #{id} limit 1")
    Plant getPlantById(int id);
}
