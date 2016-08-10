package com.imaginaryebay.Controller;

import com.imaginaryebay.Models.CommentThread;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Brian on 7/30/2016.
 */
@RestController
@RequestMapping("/comments")
public interface CommentThreadController {

    /**
     * Returns all feedback comments for a given auction item
     * @param itemId The ID for the item
     * @return CommentThread object containing all comments for the auction item with the given itemId
     *
     * Example:
     * curl -X GET "http://localhost:8080/comment/1
     *
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ResponseEntity<CommentThread> getCommentForAuction(@PathVariable("id") Long itemId);
}
