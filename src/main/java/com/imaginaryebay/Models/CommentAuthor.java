package com.imaginaryebay.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Brian on 8/1/2016.
 *
 * Represents the author of a FeedbackComment in an item's Feedback thread
 *
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentAuthor {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
