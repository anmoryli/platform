package com.anmory.platform.UserService;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-03-24 上午10:17
 */

@Setter
@Getter
@Data
public class User {
    private int id;
    private String username;
    private String password;
    private int isAdmin;
    private String email;
    private String recordNums;
    private Date createTime;
    private Date updateTime;
}
