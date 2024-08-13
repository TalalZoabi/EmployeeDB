package com.TalalZoabi.ai.utils;

import com.TalalZoabi.ai.utils.DataProcessor;

public class StringLimiterProcessor extends DataProcessor {

    private final int limit;

    public StringLimiterProcessor(int limit) {
        this.limit = limit;
    }

    @Override
    protected String preprocess(String wikiData) {
        if (wikiData == null) {
            return "";
        }
        return wikiData.length() <= limit ? wikiData : wikiData.substring(0, limit);
    }
}
