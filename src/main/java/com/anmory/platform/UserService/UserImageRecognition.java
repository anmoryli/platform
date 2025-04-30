package com.anmory.platform.UserService;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @description TODO
 * @date 2025-04-29 下午6:41
 */

@Data
public class UserImageRecognition {
    private Integer recordId;
    private Integer userId;
    private String imageName;
    private String imagePath;
    private String imageResult;
    private Float accuracy;
    private Float responseTime;
    private String modelUsed;
    private String status;
    private String errorMessage;
    private String deviceInfo;
    private Boolean isProcessed;
    private LocalDateTime createTime;
}
