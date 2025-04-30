package com.anmory.platform.UserService;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-04-29 下午6:24
 */

@Mapper
public interface UserUploadMapper {
    @Insert("insert into user_upload(user_id, upload_name, upload_path, file_type, file_size, upload_url, is_public) values(#{userId}, #{uploadName}, #{uploadPath}, #{fileType}, #{fileSize}, #{uploadUrl}, #{isPublic})")
    int insert(int userId,  String uploadName, String uploadPath, String fileType, Integer fileSize, String uploadUrl, Boolean isPublic);

    @Select("select * from user_upload order by upload_id desc")
    List<UserUpload> selectAll();
}
