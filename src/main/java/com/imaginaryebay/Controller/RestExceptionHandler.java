package com.imaginaryebay.Controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleInvalidRequest(RestException e,
                                                          ServletWebRequest request) {
        RestErrorMessage error =
                new RestErrorMessage(
                        e.getStatusCode(),
                        e.getMessage(),
                        e.getDetailedMessage(),
                        e.toString()
                );
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return handleExceptionInternal(e, error, headers, e.getStatusCode(), request);
    }
}

