package com.anmory.platform.DatabaseService.Service;

import com.anmory.platform.DatabaseService.Dao.Herb;
import com.anmory.platform.DatabaseService.Dao.Literature;
import com.anmory.platform.DatabaseService.Dao.Plant;
import com.anmory.platform.DatabaseService.Dao.Prescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-04-25 上午12:02
 */

@Service
public class GetField {
    @Autowired
    GetTable getTable;
    public Object getField(String type) {
        if(type.equals("plant")) {
            return new Plant();
        }
        if(type.equals("herb")) {
            return new Herb();
        }
        if(type.equals("literature")) {
            return new Literature();
        }
        if(type.equals("prescription")) {
            return new Prescription();
        }
        return null;
    }
}
