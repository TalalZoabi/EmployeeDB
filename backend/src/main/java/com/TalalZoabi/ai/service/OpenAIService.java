package com.TalalZoabi.ai.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OpenAIService {

    private String OPENAI_API_URL;
    private String apiKey;
    private String model;
    private int maxTokens;
    private double temperature;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public OpenAIService(String OPENAI_API_URL,
                         String apiKey,
                         String model,
                         int maxTokens,
                         double temperature,
                         RestTemplate restTemplate,
                         ObjectMapper objectMapper) {
        this.OPENAI_API_URL = OPENAI_API_URL;
        this.apiKey = apiKey;
        this.model = model;
        this.maxTokens = maxTokens;
        this.temperature = temperature;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public String generateResponse(String prompt) {
        HttpEntity<Map<String, Object>> request = createRequest(prompt);
        ResponseEntity<String> response = callOpenAI(request);
        return extractFirstChoice(response.getBody());
    }

    private HttpEntity<Map<String, Object>> createRequest(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> body = Map.of(
            "model", model,
            "messages", List.of(Map.of("role", "user", "content", prompt)),
            "max_tokens", maxTokens,
            "temperature", temperature
        );

        return new HttpEntity<>(body, headers);
    }

    private ResponseEntity<String> callOpenAI(HttpEntity<Map<String, Object>> request) {
        return restTemplate.postForEntity(OPENAI_API_URL, request, String.class);
    }

    private String extractFirstChoice(String responseBody) {
        try {
            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode choices = rootNode.path("choices");
            if (choices.isArray() && choices.size() > 0) {
                return choices.get(0).path("message").path("content").asText();
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately in production
            return "";
        }
    }
}
