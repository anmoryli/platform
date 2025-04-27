package com.anmory.platform.DatabaseService.Dao;

import lombok.Data;

import java.util.Date;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-04-26 下午3:29
 */

@Data
public class Tokens {
    private int id;
    private String token;
    private Date createTime;
    private Date updateTime;
}
