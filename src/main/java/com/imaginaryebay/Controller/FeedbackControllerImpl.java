package com.imaginaryebay.Controller;

import com.imaginaryebay.Models.Feedback;
import com.imaginaryebay.Repository.FeedbackRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by Brian on 8/2/2016.
 */
public class FeedbackControllerImpl implements FeedbackController {

    private FeedbackRepository feedbackRepository;
    public void setFeedbackRepository(FeedbackRepository feedbackRepository){
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public ResponseEntity<List<Feedback>> getAllFeedback() {
        return new ResponseEntity<>(feedbackRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Feedback>> getFeedbackForUser(@PathVariable(value = "id") Long userId) {
        return new ResponseEntity<>(feedbackRepository.findAllByUserrId(userId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Feedback>> getFeedbackForItem(@PathVariable(value = "id") Long itemId){
        return new ResponseEntity<>(feedbackRepository.findAllByItemId(itemId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Feedback> createFeedbackForItem(@PathVariable(value = "id") Long itemId, Feedback feedback) {
        System.out.println("here");
        feedback = feedbackRepository.createFeedbackForItem(feedback, itemId);
        return new ResponseEntity<>(feedback, HttpStatus.OK);
    }
}
