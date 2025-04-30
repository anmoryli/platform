package com.anmory.platform.DatabaseService.Service;

import com.anmory.platform.DatabaseService.Dao.Tokens;
import com.anmory.platform.DatabaseService.Mapper.TokensMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description TODO
 * @date 2025-04-26 下午3:31
 */

@Service
public class TokensService {
    @Autowired
    TokensMapper tokensMapper;

    public Tokens getTokens() {
        return tokensMapper.getTokens();
    }
}
