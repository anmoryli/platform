package com.anmory.platform.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description TODO
 * @date 2025-04-29 下午6:25
 */

@Service
public class UserUploadService {
    @Autowired
    UserUploadMapper userUploadMapper;

    int insert(int userId,  String uploadName, String uploadPath, String fileType, Integer fileSize, String uploadUrl, Boolean isPublic) {
        return userUploadMapper.insert(userId, uploadName, uploadPath, fileType, fileSize, uploadUrl, isPublic);
    }

    List<UserUpload> selectAll() {
        return userUploadMapper.selectAll();
    }
}
