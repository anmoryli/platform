package com.anmory.platform.DatabaseService.Service;

import org.springframework.stereotype.Service;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-04-24 下午11:57
 */

@Service
public class GetTable {
    public String getField(String type) {
        if(type.equals("植物")) {
            return "plant";
        }
        if(type.equals("药材")) {
            return "herb";
        }
        if(type.equals("文献")) {
            return "literature";
        }
        if(type.equals("药方")) {
            return "prescription";
        }
        return null;
    }
}
