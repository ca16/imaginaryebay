package com.imaginaryebay.Controller;

import com.imaginaryebay.Models.Feedback;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Brian on 7/30/2016.
 */
@RestController
@RequestMapping("/feedback")
public interface FeedbackController {

    /**
     * Returns all feedback comments for a given auction item
     * @param itemId The ID for the item
     * @return Feedback object containing all comments for the auction item with the given itemId
     *
     * Example:
     * curl -X GET "http://localhost:8080/feedback/1
     *
     */
    @RequestMapping("/{id}")
    public Feedback getFeedbackForAuction(@PathVariable("id") Long itemId);

}
