package com.imaginaryebay.Controller;

import com.imaginaryebay.Models.Feedback;
import com.imaginaryebay.Repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Brian on 7/30/2016.
 */
public class FeedbackControllerImpl implements FeedbackController{

    @Autowired
    private FeedbackRepository feedbackRepository;

    public Feedback getFeedbackForAuction(Long id){
        return feedbackRepository.getFeedbackForItem(id);
    }
}
