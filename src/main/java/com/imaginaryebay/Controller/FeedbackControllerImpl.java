package com.imaginaryebay.Controller;

import com.imaginaryebay.Models.Feedback;
import com.imaginaryebay.Models.FeedbackComment;
import com.imaginaryebay.Repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Created by Brian on 7/30/2016.
 */
public class FeedbackControllerImpl implements FeedbackController{

    @Autowired
    private FeedbackRepository feedbackRepository;

    public ResponseEntity<Feedback> getFeedbackForAuction(Long id){
        return new ResponseEntity<Feedback>(feedbackRepository.getFeedbackForItem(id), HttpStatus.OK);
    }

//    public ResponseEntity<FeedbackComment> createFeedbackCommentForItem(Long itemId, FeedbackComment feedbackComment, Long userrId){
//        return new ResponseEntity<FeedbackComment>(/*feedbackComment.createFeedbackCommentForItemAndUser(itemId, feedbackComment, userrId)*/ feedbackComment, HttpStatus.OK);
//    }
}
