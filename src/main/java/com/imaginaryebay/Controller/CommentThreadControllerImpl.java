package com.imaginaryebay.Controller;

import com.imaginaryebay.Models.CommentThread;
import com.imaginaryebay.Repository.CommentThreadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Created by Brian on 7/30/2016.
 */
public class CommentThreadControllerImpl implements CommentThreadController {

    @Autowired
    private CommentThreadRepository commentThreadRepository;

    public ResponseEntity<CommentThread> getFeedbackForAuction(Long id){
        return new ResponseEntity<>(commentThreadRepository.getFeedbackForItem(id), HttpStatus.OK);
    }
}
