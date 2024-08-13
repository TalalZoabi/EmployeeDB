package com.TalalZoabi.ai.service;

import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;

@Service
public class WikiDataFetcher {

    @Value("${spring.api.wiki.base-url}")
    private String API_URL;

    public String fetchContent(String url) {
        // Extract the article title from the URL
        String articleTitle = extractTitleFromUrl(url);
        if (articleTitle == null) {
            return null; // Handle invalid URL case
        }

        RestTemplate restTemplate = new RestTemplate();
        String apiUrl = API_URL + articleTitle;
        String response = restTemplate.getForObject(apiUrl, String.class);

        // Parse the JSON response to get the plain text extract
        JSONObject json = new JSONObject(response);
        JSONObject pages = json.getJSONObject("query").getJSONObject("pages");
        String pageId = pages.keys().next(); // Get the first key (page ID)
        return pages.getJSONObject(pageId).getString("extract");
    }

    private String extractTitleFromUrl(String url) {
        try {
            // Extract the title from the URL
            return url.substring(url.lastIndexOf("/") + 1);
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Handle extraction failure
        }
    }
}
