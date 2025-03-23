package com.anmory.platform.GraphService.Service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GetAllServiceTest {

    @Autowired
    GetAllService getAllService;
    @Test
    void getRelatedEChartsDataByName() throws IOException {
//        System.out.println(getAllService.getRelatedEChartsDataByName("川贝母"));
//        System.out.println(getAllService.getRelatedNodesByName("川贝母"));
    }

    @Test
    void query() {
        System.out.println(getAllService.query("藏红花"));
    }
}