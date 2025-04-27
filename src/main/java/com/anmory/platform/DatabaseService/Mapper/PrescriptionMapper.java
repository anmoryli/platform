package com.anmory.platform.DatabaseService.Mapper;

import com.anmory.platform.DatabaseService.Dao.Prescription;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-04-23 下午10:39
 */

@Mapper
public interface PrescriptionMapper {
    @Select("select * from medicine.prescription")
    List<Prescription> selectAll();

    @Insert("INSERT INTO prescription (" +
            "prescription_name, composition, dosage, treatment, source, suitable_population, " +
            "precautions, category, preparation_method, doctor_or_expert, status, image_url, " +
            "file_path, rating, reviews_count, version, medicine_ids, is_public,recorded_by" +
            ") VALUES (" +
            "#{prescriptionName}, #{composition}, #{dosage}, #{treatment}, #{source}, " +
            "#{suitablePopulation}, #{precautions}, #{category}, #{preparationMethod}, " +
            "#{doctorOrExpert}, #{status}, #{imageUrl}, #{filePath}, #{rating}, #{reviewsCount}, " +
            "#{version}, #{medicineIds}, #{isPublic},#{recordedBy}" +
            ")")
    int insertPrescription(
            @Param("prescriptionName") String prescriptionName,
            @Param("composition") String composition,
            @Param("dosage") String dosage,
            @Param("treatment") String treatment,
            @Param("source") String source,
            @Param("suitablePopulation") String suitablePopulation,
            @Param("precautions") String precautions,
            @Param("category") String category,
            @Param("preparationMethod") String preparationMethod,
            @Param("doctorOrExpert") String doctorOrExpert,
            @Param("status") String status,
            @Param("imageUrl") String imageUrl,
            @Param("filePath") String filePath,
            @Param("rating") Float rating,
            @Param("reviewsCount") Integer reviewsCount,
            @Param("version") String version,
            @Param("medicineIds") String medicineIds,
            @Param("isPublic") Boolean isPublic,
            @Param("recordedBy") String recordedBy
    );

    @Delete("delete from medicine.prescription where prescription_id=#{id}")
    int deletePre(int id);

    @Select("select * from medicine.prescription order by prescription_id desc limit #{offset}, #{size}")
    List<Prescription> selectPrescriptionPage(int offset, int size);

    @Select("select * from medicine.prescription where medicine.prescription.prescription_name = #{name} limit 1")
    Prescription getPrescriptionByName(String name);

    @Select("select * from medicine.prescription where medicine.prescription.prescription_id = #{id} limit 1")
    Prescription selectPrescriptionById(int id);
}
