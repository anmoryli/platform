package com.anmory.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
@SpringBootApplication
public class PlatformApplication {
	// 西南民族大学你毁了我美好的青春😒😒😒
	public static void main(String[] args) {
		SpringApplication.run(PlatformApplication.class, args);
	}
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("http://localhost:8080", "http://101.132.173.174") // 添加多个允许的源
						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
						.allowedHeaders("*") // 允许所有头部信息
						.allowCredentials(true); // 允许发送凭证（如 cookies）
			}
		};
	}
}
