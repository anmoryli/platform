package com.anmory.platform.RecognizeService.Config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description TODO
 * @date 2025-02-25 上午11:23
 */

public class CrosConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/recognize")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST");
    }
}
