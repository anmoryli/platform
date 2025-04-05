package com.anmory.platform.UserService;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-03-24 上午10:21
 */

@Mapper
public interface UserMapper {
    @Select("select * from medicine.user_info where username = #{name}")
    User getByName(String name);

    @Select("select is_admin from medicine.user_info where id=#{userId}")
    int getLevel(int userId);

    @Insert("insert into medicine.user_info (medicine.user_info.username, medicine.user_info.password) values (#{username},#{password})")
    int addUser(String username, String password);

    @Select("select email from medicine.user_info where username = #{username}")
    String getEmailByName(String username);

    @Update("update medicine.user_info set email = #{email} where medicine.user_info.username = #{username}")
    String bindEmail(String username, String email);

    @Update("update medicine.user_info set medicine.user_info.password where medicine.user_info.username = #{username}")
    int changePassword(String username, String password);

    @Select("select count(*) from medicine.user_info")
    int countUsers();
}