package com.anmory.platform.BotService.Service;

import com.anmory.platform.BotService.Mapper.Nl2sqlPromptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-04-26 上午2:00
 */

@Service
public class Nl2sqlPromptService {
    @Autowired
    Nl2sqlPromptMapper nl2sqlPromptMapper;

    public String selectPromptGenSql() {
        return nl2sqlPromptMapper.selectPromptGenSql();
    }

    public String selectPromptProcessRet() {
        return nl2sqlPromptMapper.selectPromptProcessRet();
    }
}
