package com.anmory.platform.UploadService;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-04-05 下午8:20
 */

@Mapper
public interface UserUploadMapper {
    @Insert("insert into user_upload (user_id, upload_name, upload_path) values (#{userId}, #{uploadName}, #{uploadPath});")
    int insert(int userId, String uploadName, String uploadPath);
}
