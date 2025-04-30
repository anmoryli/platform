package com.anmory.platform.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

/**
 * @description TODO
 * @date 2025-04-29 下午6:46
 */

@Service
public class UserImageRecognitionService {
    @Autowired
    UserImageRecognitionMapper userImageRecognitionMapper;

    public int insert(int userId, String imageName, String imagePath, String imageResult, Float accuracy,
                      Float responseTime, String modelUsed, String status, String errorMessage, String deviceInfo,
                      Boolean isProcessed) {
        return userImageRecognitionMapper.insert(userId, imageName, imagePath, imageResult, accuracy, responseTime,
                modelUsed, status, errorMessage, deviceInfo, isProcessed);
    }

    public List<UserImageRecognition> selectAll() {
        return userImageRecognitionMapper.selectAll();
    }
}
