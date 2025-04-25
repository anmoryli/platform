package com.anmory.platform.DatabaseService.Mapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-04-25 上午8:49
 */

@SpringBootTest
public class FileReadTest {
    @Test
    public void test() throws IOException {
        String filePath = "C:\\Users\\12901\\Downloads\\藏药植物药知识平台项目管理\\藏药植物类药材.txt";
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;
        int cnt = 0;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            for(String part : parts){
                cnt++;
                if(cnt % 144 == 0)
                    System.out.println("=============================================");
                System.out.println(part);
            }
        }
    }
}
