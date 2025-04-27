package com.anmory.platform.DatabaseService.Mapper;

import com.anmory.platform.DatabaseService.Dao.Herb;
import org.apache.ibatis.annotations.*;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-04-23 下午8:22
 */

@Mapper
public interface HerbMapper {
    @Select("select * from medicine.herb order by herb_id desc")
    List<Herb> selectAll();

    @Insert("INSERT INTO medicine.herb (" +
            "herb_name, plant_id, herb_tibetan_name, herb_alias, herb_description, part_used, " +
            "herb_features, flavor_tropism, function_indication, clinical_application, " +
            "pharmacological_effect, notes, recorded_by" +
            ") VALUES (" +
            "#{herbName},#{plantId}, #{herbTibetanName}, #{herbAlias}, #{herbDescription}, #{partUsed}, " +
            "#{herbFeatures}, #{flavorTropism}, #{functionIndication}, #{clinicalApplication}, " +
            "#{pharmacologicalEffect}, #{notes},#{recordedBy}" +
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
            @Param("notes") String notes,
            @Param("recordedBy") String recordedBy
    );

    @Select("select plant_id from medicine.plant where medicine.plant.plant_name = #{plantName}")
    Integer getPlantIdByPlantName(String plantName);

    @Delete("delete from medicine.herb where herb_id=#{id}")
    int deleteHerb(int id);

    @Select("select * from medicine.herb order by herb_id desc limit #{offset}, #{size}")
    List<Herb> selectHerbPage(int offset, int size);

    @Select("select * from medicine.herb where medicine.herb.herb_name = #{name} limit 1")
    Herb selectHerbByName(String name);

    @Select("select * from medicine.herb where medicine.herb.herb_id = #{id} limit 1")
    Herb selectHerbById(int id);
}
