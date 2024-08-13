package com.TalalZoabi.ai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.TalalZoabi.ai.service.ConversationStartersService;
import com.TalalZoabi.ai.service.OpenAIService;
import com.TalalZoabi.ai.service.WikiDataFetcher;

import com.TalalZoabi.ai.utils.DataProcessor;
import com.TalalZoabi.ai.utils.StringLimiterProcessor;

@Configuration
public class ConversationStarterConfig {

    @Bean
    public String promptTemplate() {
        return "You are an AI assistant designed to help create conversation starters. The following text is from an employee's favorite Wikipedia page. Based on this text, generate a few friendly and casual conversation starters that could be used to engage in a discussion with the employee. The tone should be light and friendly, typical of casual conversation starters. \r\n" + //
        "\r\n" + //
        "Please format the conversation starters as short sentences, each separated by a new line. Each sentence should be a simple, engaging question or statement related to the content. The goal is to make the employee feel comfortable and interested in continuing the conversation.\r\n" + //
        "\r\n" + //
        "Here is the Wikipedia content:\r\n" + //
        "\r\n" + //
        "%s\r\n" + //
        "\r\n" + //
        "Please generate 5-7 conversation starters based on this content.\r\n" + //
        "";
    }

    @Bean
    public ConversationStartersService conversationStartersService(OpenAIService openAIService, 
    WikiDataFetcher wikiDataFetcher, 
    String promptTemplate) {
        DataProcessor dataProcessor = new StringLimiterProcessor(500);
        return new ConversationStartersService(openAIService, wikiDataFetcher, promptTemplate, dataProcessor);
    }
}
