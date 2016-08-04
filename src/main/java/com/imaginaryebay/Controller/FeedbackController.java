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

    @RequestMapping(value = "item/{id}", method = RequestMethod.GET)
    ResponseEntity<List<Feedback>> getFeedbackForItem(@PathVariable(value = "id") Long itemId);

    @RequestMapping(value = "/item/{id}", method = RequestMethod.POST)
    ResponseEntity<Feedback> createFeedbackForItem(@PathVariable(value = "id") Long itemId, @RequestBody Feedback feedback);

}
