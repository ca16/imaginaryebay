package com.imaginaryebay.Controller;

import com.imaginaryebay.Models.Feedback;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ResponseEntity<Feedback> getFeedbackForAuction(@PathVariable("id") Long itemId);

    /**
     * Creates a FeedbackComment for the given auction item
     * @param itemId The ID for the item
     * @return FeedbackComment representing the comment added to the item's Disqus thread
     *
     * Example:
     * curl -X POST -H "Content-Type: application/json" -d '{"author": {"name": "Brian Gillespie"},"raw_message": "Comment on item 75"}' "http://localhost:8080/feedback/1
     *
     */
//    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
//    ResponseEntity<FeedbackComment> createFeedbackCommentForItem(@PathVariable("id") Long itemId,
//                                                                 @RequestParam FeedbackComment feedbackComment,
//                                                                 @RequestParam(required = false) Long userrId);

}
