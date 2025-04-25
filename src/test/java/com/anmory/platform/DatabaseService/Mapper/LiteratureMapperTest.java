package com.anmory.platform.DatabaseService.Mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LiteratureMapperTest {
    @Autowired
    LiteratureMapper literatureMapper;

    @Test
    void selectAll() {
        System.out.println(literatureMapper.selectAll());
    }
}