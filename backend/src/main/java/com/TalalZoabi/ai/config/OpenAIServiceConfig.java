package com.TalalZoabi.ai.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import com.TalalZoabi.ai.service.OpenAIService;

import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class OpenAIServiceConfig {
    
    @Value("${spring.ai.openai.base-url}")
    private String OPENAI_API_URL;

    @Value("${spring.ai.openai.api-key}")
    private String apiKey;

    @Value("${spring.ai.openai.model}")
    private String model;

    @Value("${spring.ai.openai.max-tokens}")
    private int maxTokens;

    @Value("${spring.ai.openai.temperature}")
    private double temperature;



    @Bean
    public OpenAIService openAIService() {
        return new OpenAIService(OPENAI_API_URL, apiKey, model, maxTokens, temperature, new RestTemplate(), new ObjectMapper());
    }

}
