package com.anmory.platform.VisualizeService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VisualizeMapperTest {

    @Autowired
    VisualizeMapper visualizeMapper;
    @Test
    void getDailyRegistrationTrend() {
        System.out.println(visualizeMapper.getDailyRegistrationTrend(LocalDateTime.now().minusDays(7), LocalDateTime.now()));
    }

    @Test
    void getRegistrationByEmailDomain() {
        System.out.println(visualizeMapper.getRegistrationByEmailDomain(LocalDateTime.now().minusDays(7), LocalDateTime.now()));
    }

    @Test
    void getMonthlyActiveUsers() {
        System.out.println(visualizeMapper.getMonthlyActiveUsers(LocalDateTime.now().minusMonths(1), LocalDateTime.now()));
    }

    @Test
    void getFunctionUsageFrequency() {
        System.out.println(visualizeMapper.getFunctionUsageFrequency(LocalDateTime.now().minusDays(7), LocalDateTime.now()));
    }

    @Test
    void getUploadStatusDistribution() {
        System.out.println(visualizeMapper.getUploadStatusDistribution(LocalDateTime.now().minusDays(7), LocalDateTime.now()));
    }

    @Test
    void getImageRecognitionAccuracy() {
        System.out.println(visualizeMapper.getImageRecognitionAccuracy(LocalDateTime.now().minusDays(7), LocalDateTime.now()));
    }

    @Test
    void getStorageUsageByFileType() {
        System.out.println(visualizeMapper.getStorageUsageByFileType(LocalDateTime.now().minusDays(7), LocalDateTime.now()));
    }

    @Test
    void getImageRecognitionAccuracyByModel() {
        System.out.println(visualizeMapper.getImageRecognitionAccuracyByModel(LocalDateTime.now().minusDays(7), LocalDateTime.now()));
    }

    @Test
    void getConversationResponseTimeDistribution() {
        System.out.println(visualizeMapper.getConversationResponseTimeDistribution(LocalDateTime.now().minusDays(7), LocalDateTime.now()));
    }

    @Test
    void getKnowledgeSearchSuccessRate() {
        System.out.println(visualizeMapper.getKnowledgeSearchSuccessRate(LocalDateTime.now().minusDays(7), LocalDateTime.now()));
    }

    @Test
    void getHerbDescriptionMissingRate() {
        System.out.println(visualizeMapper.getHerbDescriptionMissingRate());
    }

    @Test
    void getPrescriptionByCategory() {
        System.out.println(visualizeMapper.getPrescriptionByCategory(LocalDateTime.now().minusDays(7), LocalDateTime.now()));
    }

    @Test
    void getLiteratureByResearchField() {
        System.out.println(visualizeMapper.getLiteratureByResearchField(LocalDateTime.now().minusDays(7), LocalDateTime.now()));
    }

    @Test
    void getUploadFileTypeDistribution() {
        System.out.println(visualizeMapper.getUploadFileTypeDistribution(LocalDateTime.now().minusDays(7), LocalDateTime.now()));
    }

    @Test
    void getConversationLanguageDistribution() {
        System.out.println(visualizeMapper.getConversationLanguageDistribution(LocalDateTime.now().minusDays(7), LocalDateTime.now()));
    }
}