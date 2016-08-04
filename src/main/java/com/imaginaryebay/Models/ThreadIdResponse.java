package com.imaginaryebay.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Brian on 8/2/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ThreadIdResponse {

    private ThreadId response;

    public ThreadId getResponse() {
        return response;
    }

    public void setResponse(ThreadId response) {
        this.response = response;
    }
}
