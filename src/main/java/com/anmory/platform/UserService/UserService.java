package com.anmory.platform.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-03-24 下午12:46
 */

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    User getByName(String username) {
        return userMapper.getByName(username);
    }

    int getLevel(int userId) {
        return userMapper.getLevel(userId);
    }

    int addUser(String username, String password) {
        return userMapper.addUser(username, password);
    }

    String getEmailByName(String username) {
        return userMapper.getEmailByName(username);
    }

    String bindEmail(String username, String email) {
        return userMapper.bindEmail(username, email);
    }

    int changePassword(String username, String password) {
        return userMapper.changePassword(username, password);
    }

    public int countUsers() {
        return userMapper.countUsers();
    }

}
