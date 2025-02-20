package com.ishizuka.demo.application.services.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }
}
