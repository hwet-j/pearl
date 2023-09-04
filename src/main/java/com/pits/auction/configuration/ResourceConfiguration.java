package com.pits.auction.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ResourceConfiguration implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:///C:/Auction/Image/"); // 로컬 경로 설정

        // 오디오 리소스 핸들러 추가
        registry.addResourceHandler("/audios/**")
                .addResourceLocations("file:///C:/Auction/Audio/");
    }


}