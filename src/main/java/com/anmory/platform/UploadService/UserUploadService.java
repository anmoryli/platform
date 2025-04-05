package com.anmory.platform.UploadService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-04-05 下午8:21
 */

@Service
public class UserUploadService {
    @Autowired
    UserUploadMapper userUploadMapper;

    public int insert(int userId, String uploadName, String uploadPath) {
        return userUploadMapper.insert(userId, uploadName, uploadPath);
    }
}
