package com.anmory.platform.RecordService.service;

import com.anmory.platform.RecordService.mapper.UserImageRecognitionMapper;
import com.anmory.platform.RecordService.model.UserImageRecognition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-04-05 下午4:06
 */

@Service
public class UserImageRecognitionService {
    @Autowired
    UserImageRecognitionMapper userImageRecognitionMapper;

    public List<UserImageRecognition> getAll(){
        return userImageRecognitionMapper.selectAll();
    }

    public int insert(int userId,String imageName,String imagePath,String imageResult){
        return userImageRecognitionMapper.insert(userId,imageName,imagePath,imageResult);
    }
}
