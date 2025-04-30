package com.anmory.platform.VisualizeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class VisualizeService {

    @Autowired
    private VisualizeMapper visualizeMapper;

    public List<Map<String, Object>> getDailyRegistrationTrend(LocalDateTime startTime, LocalDateTime endTime) {
        return visualizeMapper.getDailyRegistrationTrend(startTime, endTime);
    }

    public List<Map<String, Object>> getRegistrationByEmailDomain(LocalDateTime startTime, LocalDateTime endTime) {
        return visualizeMapper.getRegistrationByEmailDomain(startTime, endTime);
    }

    public List<Map<String, Object>> getMonthlyActiveUsers(LocalDateTime startTime, LocalDateTime endTime) {
        return visualizeMapper.getMonthlyActiveUsers(startTime, endTime);
    }

    public List<Map<String, Object>> getFunctionUsageFrequency(LocalDateTime startTime, LocalDateTime endTime) {
        return visualizeMapper.getFunctionUsageFrequency(startTime, endTime);
    }

    public List<Map<String, Object>> getUploadStatusDistribution(LocalDateTime startTime, LocalDateTime endTime) {
        return visualizeMapper.getUploadStatusDistribution(startTime, endTime);
    }

    public Map<String, Object> getImageRecognitionAccuracy(LocalDateTime startTime, LocalDateTime endTime) {
        return visualizeMapper.getImageRecognitionAccuracy(startTime, endTime);
    }

    public List<Map<String, Object>> getStorageUsageByFileType(LocalDateTime startTime, LocalDateTime endTime) {
        return visualizeMapper.getStorageUsageByFileType(startTime, endTime);
    }

    public List<Map<String, Object>> getImageRecognitionAccuracyByModel(LocalDateTime startTime, LocalDateTime endTime) {
        return visualizeMapper.getImageRecognitionAccuracyByModel(startTime, endTime);
    }

    public List<Map<String, Object>> getConversationResponseTimeDistribution(LocalDateTime startTime, LocalDateTime endTime) {
        return visualizeMapper.getConversationResponseTimeDistribution(startTime, endTime);
    }

    public List<Map<String, Object>> getKnowledgeSearchSuccessRate(LocalDateTime startTime, LocalDateTime endTime) {
        return visualizeMapper.getKnowledgeSearchSuccessRate(startTime, endTime);
    }

    public Map<String, Object> getHerbDescriptionMissingRate() {
        return visualizeMapper.getHerbDescriptionMissingRate();
    }

    public List<Map<String, Object>> getPrescriptionByCategory(LocalDateTime startTime, LocalDateTime endTime) {
        return visualizeMapper.getPrescriptionByCategory(startTime, endTime);
    }

    public List<Map<String, Object>> getLiteratureByResearchField(LocalDateTime startTime, LocalDateTime endTime) {
        return visualizeMapper.getLiteratureByResearchField(startTime, endTime);
    }

    public List<Map<String, Object>> getUploadFileTypeDistribution(LocalDateTime startTime, LocalDateTime endTime) {
        return visualizeMapper.getUploadFileTypeDistribution(startTime, endTime);
    }

    public List<Map<String, Object>> getConversationLanguageDistribution(LocalDateTime startTime, LocalDateTime endTime) {
        return visualizeMapper.getConversationLanguageDistribution(startTime, endTime);
    }
}