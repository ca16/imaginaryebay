package com.imaginaryebay.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Brian on 8/1/2016.
 *
 * Represents the author of a Comment in an item's CommentThread
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
