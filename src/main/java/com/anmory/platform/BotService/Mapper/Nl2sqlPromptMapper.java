package com.anmory.platform.BotService.Mapper;

import com.anmory.platform.BotService.Model.Nl2sqlPrompt;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @description TODO
 * @date 2025-04-26 上午1:43
 */

@Mapper
public interface Nl2sqlPromptMapper {
    @Select("select prompt_gen_sql from medicine.nl2sql_prompt")
    String selectPromptGenSql();

    @Select("select prompt_process_ret from medicine.nl2sql_prompt")
    String selectPromptProcessRet();
}
