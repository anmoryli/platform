package com.anmory.platform.DatabaseService.Mapper;

import com.anmory.platform.DatabaseService.Dao.Prescription;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
            "file_path, rating, reviews_count, version, medicine_ids, is_public" +
            ") VALUES (" +
            "#{prescriptionName}, #{composition}, #{dosage}, #{treatment}, #{source}, " +
            "#{suitablePopulation}, #{precautions}, #{category}, #{preparationMethod}, " +
            "#{doctorOrExpert}, #{status}, #{imageUrl}, #{filePath}, #{rating}, #{reviewsCount}, " +
            "#{version}, #{medicineIds}, #{isPublic}" +
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
            @Param("isPublic") Boolean isPublic
    );
}
