package com.anmory.platform.RecordService.mapper;

import com.anmory.platform.RecordService.model.UserImageRecognition;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-04-05 下午4:01
 */
@Mapper
public interface UserImageRecognitionMapper {
    @Select("select * from user_image_recognition where user_id = #{userId}")
    List<UserImageRecognition> selectAll(int userId);

    @Insert("insert into user_image_recognition (user_id,image_name,image_path,image_result) values (#{userId},#{imageName},#{imagePath},#{imageResult})")
    int insert(int userId,String imageName,String imagePath,String imageResult);

    @Select("select count(*) from medicine.user_image_recognition")
    int countImages();
}
