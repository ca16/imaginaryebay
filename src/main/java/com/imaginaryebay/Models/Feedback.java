package com.imaginaryebay.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by Brian on 8/1/2016.
 *
 * Represents the total Feedback thread for a given auction item.
 * Includes all FeedbackComments and their authors
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Feedback {

    private List<FeedbackComment> response;

    public List<FeedbackComment> getResponse() {
        return response;
    }

    public void setResponse(List<FeedbackComment> response) {
        this.response = response;
    }
}
