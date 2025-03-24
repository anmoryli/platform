package com.anmory.platform.UserService;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
    private String userName;
    private String password;
    private int isAdmin;
    private String email;
}
