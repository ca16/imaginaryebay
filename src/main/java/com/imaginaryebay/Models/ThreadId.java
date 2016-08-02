package com.imaginaryebay.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Brian on 8/2/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ThreadId {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.id;
    }
}
