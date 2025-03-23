package com.anmory.platform.DataService.Mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HerbMapperTest {

    @Autowired
    HerbMapper herbMapper;
    @Test
    void queryAll() {
        System.out.println(herbMapper.queryAll());
    }
}