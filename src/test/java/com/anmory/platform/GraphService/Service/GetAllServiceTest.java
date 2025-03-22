package com.anmory.platform.GraphService.Service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GetAllServiceTest {

    @Autowired
    GetAllService getAllService;
    @Test
    void getRelatedEChartsDataByName() {
        System.out.println(getAllService.getRelatedEChartsDataByName("雪莲花"));
    }
}