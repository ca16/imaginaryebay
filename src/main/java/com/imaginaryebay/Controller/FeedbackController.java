package com.imaginaryebay.Controller;

import com.imaginaryebay.Models.Feedback;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Brian on 8/2/2016.
 */
@RestController
@RequestMapping("/feedback")
public interface FeedbackController {

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<List<Feedback>> getAllFeedback();

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    ResponseEntity<List<Feedback>> getFeedbackForUser(@PathVariable(value = "id") Long userId);

    /**
     * Returns all feedback for a given auction item
     * @param itemId The ID for the item
     * @return List of Feedback  representing all feedback for a given Item
     *
     * Example:
     * curl -X POST -d '{"content":"This was the worst product I ever bought!"}' "http://localhost:8080/feedback/1"
     *
     */
    @RequestMapping(value = "/item/{id}", method = RequestMethod.GET)
    ResponseEntity<List<Feedback>> getFeedbackForItem(@PathVariable(value = "id") Long itemId);

    /**
     * Returns all feedback for a given auction item
     * @param itemId The ID for the item
     * @param feedback A user's feedback for a given item
     * @return Feedback object representing the Userr's feedback for a given Item
     *
     * Example:
     * curl -X POST -d '{"content":"This was the worst product I ever bought!"}' "http://localhost:8080/feedback/1"
     *
     */
    @RequestMapping(value = "/item/{id}", method = RequestMethod.POST)
    ResponseEntity<Feedback> createFeedbackForItem(@PathVariable(value = "id") Long itemId, @RequestBody Feedback feedback);
}
