package com.imaginaryebay.Controller;

import org.springframework.http.HttpStatus;

public class RestErrorMessage {

    private HttpStatus status;
    private String message;
    private String detailedMessage;
    private String exceptionMessage;
    public RestErrorMessage(HttpStatus status, String message,
                            String detailedMessage, String exceptionMessage) {
        this.status = status;
        this.message = message;
        this.detailedMessage = detailedMessage;
        this.exceptionMessage = exceptionMessage;
    }

    public HttpStatus getStatus() {
        return status;
    }
    public String getMessage() {
        return message;
    }
    public String getDetailedMessage() {
        return detailedMessage;
    }
    public String getExceptionMessage() {
        return exceptionMessage;
    }
}