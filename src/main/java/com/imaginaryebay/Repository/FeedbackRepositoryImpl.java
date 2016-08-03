package com.imaginaryebay.Repository;

import com.imaginaryebay.Controller.RestException;
import com.imaginaryebay.DAO.BiddingDAO;
import com.imaginaryebay.DAO.FeedbackDAO;
import com.imaginaryebay.DAO.ItemDAO;
import com.imaginaryebay.DAO.UserrDao;
import com.imaginaryebay.Models.Bidding;
import com.imaginaryebay.Models.Feedback;
import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Models.Userr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by Brian on 8/2/2016.
 */
@Transactional
public class FeedbackRepositoryImpl implements FeedbackRepository{

    private FeedbackDAO feedbackDAO;
    public void setFeedbackDAO(FeedbackDAO feedbackDAO){
        this.feedbackDAO = feedbackDAO;
    }

    @Autowired
    private UserrDao userrDao;

    @Autowired
    private ItemDAO itemDAO;

    @Autowired
    private BiddingDAO biddingDAO;

    private static final String NOT_AVAILABLE = "Not available.";

    @Override
    public Feedback save(Feedback feedback) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth == null || !auth.isAuthenticated()) {
//            throw new RestException(NOT_AVAILABLE, "You must be logged in to create an item.", HttpStatus.UNAUTHORIZED);
//        }

        Item feedbackItem = feedback.getItem();
        if (feedbackItem == null || feedback.getContent() == null){
            throw new RestException("Malformed request.", "A required request parameter was missing.", HttpStatus.BAD_REQUEST);
        }
        if (feedback.getTimestamp() == null){
            Date date= new Date();
            feedback.setTimestamp(new Timestamp(date.getTime()));
        }
        // If auction is over
        if (feedback.getTimestamp().before(feedbackItem.getEndtime())){
            throw new RestException(NOT_AVAILABLE, "You are not allowed to provide feedback for this item yet.", HttpStatus.FORBIDDEN);
        }

        Bidding winningBid = biddingDAO.getHighestBiddingForItem(feedbackItem.getId());
        Userr bidWinner = winningBid.getUserr();
//
//        if (!auth.getName().equals(bidWinner.getEmail())){
//            throw new RestException(NOT_AVAILABLE, "You are not authorized to provide feedback for this item.", HttpStatus.UNAUTHORIZED);
//        }
        feedbackDAO.persist(feedback);
        return feedback;
    }

    @Override
    public Feedback findById(Long id) {

        Feedback feedback = feedbackDAO.findById(id);
        if (feedback == null){
            throw new RestException(NOT_AVAILABLE, "The requested feedback does not exist.", HttpStatus.OK);
        }
        return feedback;
    }

    @Override
    public List<Feedback> findAll() {

        List<Feedback> feedbacks = feedbackDAO.findAll();
        if (feedbacks == null || feedbacks.size() == 0){
            throw new RestException(NOT_AVAILABLE, "No feedback found.", HttpStatus.OK);
        }
        return feedbacks;
    }

    @Override
    public List<Feedback> findAllByUserrId(Long userrId) {

        Userr requestUserr = userrDao.getUserrByID(userrId);
        if (requestUserr == null){
            throw new RestException(NOT_AVAILABLE, "The requested user does note exist.", HttpStatus.OK);
        }

        List<Feedback> feedbacks = feedbackDAO.findAllByUserrId(userrId);
        if (feedbacks == null || feedbacks.size() == 0){
            throw new RestException(NOT_AVAILABLE, "No feedback found for this user.", HttpStatus.OK);
        }

        return feedbacks;
    }

    @Override
    public List<Feedback> findAllByItemId(Long itemId) {

        Item requestItem = itemDAO.findByID(itemId);
        if (requestItem == null) {
            throw new RestException(NOT_AVAILABLE, "The requested item does note exist.", HttpStatus.OK);
        }

        List<Feedback> feedbacks = feedbackDAO.findAllByItemId(itemId);
        if (feedbacks == null || feedbacks.size() == 0) {
            throw new RestException(NOT_AVAILABLE, "There is no feedback for this item", HttpStatus.OK);
        }
        return feedbacks;
    }
}
