package com.anmory.platform.UserService;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @description TODO
 * @date 2025-04-29 下午6:24
 */

@Data
public class UserUpload {
    private Integer uploadId;
    private Integer userId;
    private String uploadName;
    private String uploadPath;
    private String fileType;
    private Integer fileSize;
    private String uploadUrl;
    private Boolean isPublic;
    private String status;
    private String errorMessage;
    private LocalDateTime timestamp;
}
