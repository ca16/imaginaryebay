package com.imaginaryebay.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Brian on 8/1/2016.
 *
 * Represents a comment in an auction item's Feedback thread
 * Contains the CommentAuthor and String representing the raw comment
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FeedbackComment {

    private CommentAuthor author;
    private String raw_message;

    public CommentAuthor getAuthor() {
        return author;
    }

    public void setAuthor(CommentAuthor author) {
        this.author = author;
    }

    public String getRaw_message() {
        return raw_message;
    }

    public void setRaw_message(String raw_message) {
        this.raw_message = raw_message;
    }
}
