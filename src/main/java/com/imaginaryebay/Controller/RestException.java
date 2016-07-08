package com.imaginaryebay.Controller;


import org.springframework.http.HttpStatus;

public class RestException extends RuntimeException {

    private String message;
    private String detailedMessage;
    private HttpStatus statusCode;

    public RestException(String message, String detailedMessage, HttpStatus statusCode) {
        this.message = message;
        this.detailedMessage = detailedMessage;
        this.statusCode = statusCode;
    }

    public RestException(String message, String detailedMessage) {
        this.message = message;
        this.detailedMessage = detailedMessage;
        this.statusCode = HttpStatus.OK;
    }

    public String getMessage() {
        return this.message;
    }
    public String getDetailedMessage() {
        return this.detailedMessage;
    }
    public HttpStatus getStatusCode() { return this.statusCode; }
}