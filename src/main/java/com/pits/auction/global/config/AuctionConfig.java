package com.pits.auction.global.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuctionConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
