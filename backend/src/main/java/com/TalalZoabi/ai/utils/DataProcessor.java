package com.TalalZoabi.ai.utils;

public abstract class DataProcessor {

    // Method to process the wiki data
    public String process(String wikiData) {
        // Basic processing (could be overridden or extended by subclasses)
        return preprocess(wikiData);
    }

    // Hook method for subclasses to override specific processing behavior
    protected abstract String preprocess(String wikiData);
}
