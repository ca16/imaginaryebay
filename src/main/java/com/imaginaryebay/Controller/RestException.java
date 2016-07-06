package com.imaginaryebay.Controller;

public class RestException extends RuntimeException {

    private String message;
    private String detailedMessage;

    public RestException(String message, String detailedMessage) {
        this.message = message;
        this.detailedMessage = detailedMessage;
    }

    public String getMessage() {
        return message;
    }
    public String getDetailedMessage() {
        return detailedMessage;
    }
}