package com.codechallenge.trackfinder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TrackFinderConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
