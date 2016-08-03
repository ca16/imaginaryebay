package com.imaginaryebay.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by Brian on 8/1/2016.
 *
 * Represents the total CommentThread thread for a given auction item.
 * Includes all FeedbackComments and their authors
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentThread {

    private List<Comment> response;

    public List<Comment> getResponse() {
        return response;
    }

    public void setResponse(List<Comment> response) {
        this.response = response;
    }
}
