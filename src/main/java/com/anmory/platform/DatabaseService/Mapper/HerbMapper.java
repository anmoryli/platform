package com.anmory.platform.DatabaseService.Mapper;

import com.anmory.platform.DatabaseService.Dao.Herb;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-04-23 下午8:22
 */

@Mapper
public interface HerbMapper {
    @Select("select * from medicine.herb")
    List<Herb> selectAll();

    @Insert("INSERT INTO medicine.herb (" +
            "herb_name, plant_id, herb_tibetan_name, herb_alias, herb_description, part_used, " +
            "herb_features, flavor_tropism, function_indication, clinical_application, " +
            "pharmacological_effect, notes" +
            ") VALUES (" +
            "#{herbName},#{plantId}, #{herbTibetanName}, #{herbAlias}, #{herbDescription}, #{partUsed}, " +
            "#{herbFeatures}, #{flavorTropism}, #{functionIndication}, #{clinicalApplication}, " +
            "#{pharmacologicalEffect}, #{notes}" +
            ")")
    int insertHerb(
            @Param("herbName") String herbName,
            @Param("plantId") int plantId,
            @Param("herbTibetanName") String herbTibetanName,
            @Param("herbAlias") String herbAlias,
            @Param("herbDescription") String herbDescription,
            @Param("partUsed") String partUsed,
            @Param("herbFeatures") String herbFeatures,
            @Param("flavorTropism") String flavorTropism,
            @Param("functionIndication") String functionIndication,
            @Param("clinicalApplication") String clinicalApplication,
            @Param("pharmacologicalEffect") String pharmacologicalEffect,
            @Param("notes") String notes
    );

    @Select("select * from medicine.plant where medicine.plant.plant_name = #{plantName}")
    int getPlantIdByPlantName(String plantName);
}
