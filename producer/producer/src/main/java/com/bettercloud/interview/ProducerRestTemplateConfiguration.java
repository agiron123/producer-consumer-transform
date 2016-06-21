package com.bettercloud.interview;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.bettercloud.interview")
public class ProducerRestTemplateConfiguration {
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}