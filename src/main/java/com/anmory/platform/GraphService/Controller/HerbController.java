package com.anmory.platform.GraphService.Controller;

import com.anmory.platform.GraphService.Dao.Herb;
import com.anmory.platform.GraphService.Service.HerbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class HerbController {
    @Autowired
    private HerbService herbService;

    @PostMapping("/createherb")
    public Herb createHerb(@RequestParam String name) {
        System.out.println("name = " + name);
        return herbService.createHerb(name);
    }

    @GetMapping("/findherb")
    public Herb getHerbByName(@RequestParam String name) {
        System.out.println("name = " + name);
        return herbService.findHerbByName(name);
    }
}