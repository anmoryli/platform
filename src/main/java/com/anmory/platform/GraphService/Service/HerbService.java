package com.anmory.platform.GraphService.Service;

import com.anmory.platform.GraphService.Dao.Herb;
import com.anmory.platform.GraphService.Repository.HerbRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HerbService {
    private static final Logger logger = LoggerFactory.getLogger(HerbService.class);

    @Autowired
    private HerbRepo herbRepo;

    public Herb createHerb(String name) {
        logger.info("Creating herb with name: {}", name);
        Herb herb = new Herb(name);
        return herbRepo.save(herb);
    }

    public Herb findHerbByName(String name) {
        logger.info("Finding herb by name: {}", name);
        return herbRepo.findByName(name); // 返回 null 如果未找到
    }
}