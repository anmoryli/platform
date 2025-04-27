package com.anmory.platform.DatabaseService.Mapper;

import com.anmory.platform.DatabaseService.Dao.Tokens;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-04-26 下午3:30
 */

@Mapper
public interface TokensMapper {
    @Select("select * from tokens limit 1")
    Tokens getTokens();
}
