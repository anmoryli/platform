package com.anmory.platform.UserService;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-04-29 下午6:42
 */

@Mapper
public interface UserImageRecognitionMapper {
    @Select("select * from user_image_recognition")
    List<UserImageRecognition> selectAll();

    @Insert(
            "insert into user_image_recognition(user_id, image_name, image_path, " +
                    "image_result, accuracy, response_time, model_used, status, error_message, " +
                    "device_info, is_processed) values(#{userId}, #{imageName}, #{imagePath}, #{imageResult}, " +
                    "#{accuracy}, #{responseTime}, #{modelUsed}, #{status}, #{errorMessage}, #{deviceInfo}, #{isProcessed})"
    )
    int insert(int userId, String imageName, String imagePath, String imageResult, Float accuracy,
               Float responseTime, String modelUsed, String status, String errorMessage, String deviceInfo,
               Boolean isProcessed);
}
