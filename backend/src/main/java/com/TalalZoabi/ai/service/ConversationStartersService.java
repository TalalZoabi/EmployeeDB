package com.TalalZoabi.ai.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.TalalZoabi.ai.exception.WikiDataNotFoundException;
import com.TalalZoabi.ai.model.Employee;

import com.TalalZoabi.ai.utils.DataProcessor;

@Service
public class ConversationStartersService {

    private final OpenAIService openAIService;
    private final WikiDataFetcher wikiDataFetcher;
    private final String promptTemplate;
    private final DataProcessor dataProcessor;

    @Autowired
    public ConversationStartersService(OpenAIService openAIService, 
    WikiDataFetcher wikiDataFetcher, 
    String promptTemplate, 
    DataProcessor dataProcessor) {
        this.openAIService = openAIService;
        this.wikiDataFetcher = wikiDataFetcher;
        this.promptTemplate = promptTemplate;
        this.dataProcessor = dataProcessor;
    }

    public String getWikiData(Employee employee) {
        try {
            return wikiDataFetcher.fetchContent(employee.getFavoriteWikiPage());
        } catch (Exception e) {
            throw new WikiDataNotFoundException("An error occurred while fetching wiki data for page: " + employee.getFavoriteWikiPage(), e);
        }
    }

    public List<String> getConversationStarters(Employee employee) {
        String wikiContent;
        try {
            wikiContent = wikiDataFetcher.fetchContent(employee.getFavoriteWikiPage());
            if (wikiContent == null || wikiContent.isEmpty()) {
                throw new WikiDataNotFoundException("Wiki data not found for the page: " + employee.getFavoriteWikiPage());
            }
        } catch (Exception e) {
            throw new WikiDataNotFoundException("An error occurred while fetching wiki data for page: " + employee.getFavoriteWikiPage(), e);
        }

        String processedWikiContent = dataProcessor.process(wikiContent);

        // Define a prompt template
        String prompt = String.format(this.promptTemplate, processedWikiContent);

        try {
            String response = openAIService.generateResponse(prompt);
            return List.of(response.split("\n"));
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate conversation starters" + e.getMessage(), e);
        }
    }
}
