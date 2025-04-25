package com.anmory.platform.DatabaseService.Controller;

import com.anmory.platform.DatabaseService.Service.HerbServiceR;
import com.anmory.platform.DatabaseService.Service.LiteratureService;
import com.anmory.platform.DatabaseService.Service.PlantService;
import com.anmory.platform.DatabaseService.Service.PrescriptionService;
import com.anmory.platform.UserService.User;
import com.anmory.platform.UserService.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-04-23 下午2:35
 */

@RestController
@RequestMapping("/add")
public class AddController {
    @Autowired
    PlantService plantService;
    @Autowired
    HerbServiceR herbServiceR;
    @Autowired
    PrescriptionService prescriptionService;
    @Autowired
    LiteratureService literatureService;
    @Autowired
    UserService userService;

    @RequestMapping("/addPlant")
    public int addPlant(String plantName,
                        String plantLatin,
                        String plantAlias,
                        String plantTibetanName,
                        String plantFamilyName,
                        String plantGenusName,
                        MultipartFile file,
                        String plantFeatures,
                        String plantOrigin,
                        String plantProtectLevel,
                        String notes,
                        HttpSession session
    ) throws IOException {
        User user = (User) session.getAttribute("session_user_key");
        if(user == null) return 0;
        userService.addRecordNums(user.getUsername());
        String imgPath = "/usr/local/nginx/images/plant/" + file.getOriginalFilename();
        File dir = new File("/usr/local/nginx/images/plant/");
        if (!dir.exists()) {
            dir.mkdirs();
        }
//        String imgPath = "D:/testImg/" + file.getOriginalFilename();
//        File dir = new File("D:/testImg/");
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }

        FileOutputStream fos = new FileOutputStream(new File(imgPath));
        fos.write(file.getBytes());
        System.out.println("图片上传成功");
        fos.close();
        System.out.println(imgPath);

        return plantService.insertPlant(plantName,
                plantLatin,
                plantAlias,
                plantTibetanName,
                plantFamilyName,
                plantGenusName,
                file.getOriginalFilename(),
                plantFeatures,
                plantOrigin,
                plantProtectLevel,
                notes
        );
    }

    @RequestMapping("/addHerb")
    public int addHerb(String herbName,
                       String plantName,
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
                       HttpSession session) {
        try {
            User user = (User) session.getAttribute("session_user_key");
            if(user == null) return 0;
            userService.addRecordNums(user.getUsername());
            int plantId = herbServiceR.getPlantIdByPlantName(plantName);
            System.out.println(plantId);
            return herbServiceR.insertHerb(
                    herbName,
                    plantId,
                    herbTibetanName,
                    herbAlias,
                    herbDescription,
                    partUsed,
                    herbFeatures,
                    flavorTropism,
                    functionIndication,
                    clinicalApplication,
                    pharmacologicalEffect,
                    notes
            );
        }catch (Exception e) {
            return 0;
        }
    }

    @RequestMapping("/addPrescription")
    public int addPrescription(
            @RequestParam String prescriptionName,
            @RequestParam(required = false) String composition,
            @RequestParam(required = false) String dosage,
            @RequestParam(required = false) String treatment,
            @RequestParam(required = false) String source,
            @RequestParam(required = false) String suitablePopulation,
            @RequestParam(required = false) String precautions,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String preparationMethod,
            @RequestParam(required = false) String doctorOrExpert,
            @RequestParam(defaultValue = "启用") String status,
            @RequestParam(required = false) MultipartFile file,
            @RequestParam(defaultValue = "0") Float rating,
            @RequestParam(defaultValue = "0") Integer reviewsCount,
            @RequestParam(defaultValue = "1.0") String version,
            @RequestParam(required = false) String medicineIds,
            @RequestParam(defaultValue = "true") Boolean isPublic,
            HttpSession session
    ) throws IOException {
        User user = (User) session.getAttribute("session_user_key");
        if(user == null) return 0;
        userService.addRecordNums(user.getUsername());
        String imageUrl = file.getOriginalFilename();
        String filePath = "/usr/local/nginx/images/prescription/" + file.getOriginalFilename();

        File dir = new File("/usr/local/nginx/images/prescription/");
        if (!dir.exists()) {
            dir.mkdirs();
        }

//        String filePath = "D:/testImg/" + file.getOriginalFilename();
//        File dir = new File("D:/testImg/");
//        if (!dir.exists()) {
//            dir.mkdirs();
//        }

        FileOutputStream fos = new FileOutputStream(new File(filePath));
        fos.write(file.getBytes());
        System.out.println("图片上传成功");
        fos.close();
        return prescriptionService.insertPrescription(
                prescriptionName,
                composition,
                dosage,
                treatment,
                source,
                suitablePopulation,
                precautions,
                category,
                preparationMethod,
                doctorOrExpert,
                status,
                imageUrl,
                filePath,
                rating,
                reviewsCount,
                version,
                medicineIds,
                isPublic
        );
    }

    @RequestMapping("/addLiterature")
    public int addLiterature(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String tibetanTitle,
            @RequestParam(required = false) String authors,
            @RequestParam(required = false) Integer publicationYear,
            @RequestParam(required = false) String journalOrPublisher,
            @RequestParam(required = false) String volumeIssue,
            @RequestParam(required = false) String pages,
            @RequestParam(required = false) String abstractText,
            @RequestParam(required = false) String downloadLink,
            @RequestParam(required = false) String filePath,
            @RequestParam(required = false) String mainPlant,
            @RequestParam(required = false) String researchField,
            @RequestParam(required = false) String keywords,
            HttpSession session
    ) {
        User user = (User) session.getAttribute("session_user_key");
        if(user == null) return 0;
        userService.addRecordNums(user.getUsername());
        return literatureService.insertLiterature(
                title,
                tibetanTitle,
                authors,
                publicationYear,
                journalOrPublisher,
                volumeIssue,
                pages,
                abstractText,
                downloadLink,
                filePath,
                mainPlant,
                researchField,
                keywords
        );
    }
}