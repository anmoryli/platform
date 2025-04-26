package com.anmory.platform.DatabaseService.Service;

import com.anmory.platform.DatabaseService.Dao.Prescription;
import com.anmory.platform.DatabaseService.Mapper.PrescriptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-04-23 下午10:41
 */

@Service
public class PrescriptionService {
    @Autowired
    PrescriptionMapper prescriptionMapper;

    public int insertPrescription(String prescriptionName, String composition, String dosage, String treatment, String source, String suitablePopulation, String precautions, String category, String preparationMethod, String doctorOrExpert, String status, String imageUrl, String filePath, Float rating, Integer reviewsCount, String version, String medicineIds, Boolean isPublic, String recordedBy) {
        return prescriptionMapper.insertPrescription(prescriptionName, composition, dosage, treatment, source, suitablePopulation, precautions, category, preparationMethod, doctorOrExpert, status, imageUrl, filePath, rating, reviewsCount, version, medicineIds, isPublic,recordedBy);
    }

    public List<Prescription> selectAll() {
        return prescriptionMapper.selectAll();
    }

    public int deletePre(int id) {
        return prescriptionMapper.deletePre(id);
    }

    public List<Prescription> selectPrescriptionPage(int offset, int size) {
        return prescriptionMapper.selectPrescriptionPage(offset, size);
    }

    public Prescription getPrescriptionByName(String name) {
        return prescriptionMapper.getPrescriptionByName(name);
    }
}
