package com.TalalZoabi.ai.exception;


public class WikiDataNotFoundException extends RuntimeException {
    public WikiDataNotFoundException(String message) {
        super(message);
    }

    public WikiDataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
