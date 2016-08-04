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
    public Feedback createFeedbackForItem(Feedback feedback, Long itemId) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth == null || !auth.isAuthenticated()) {
//            throw new RestException(NOT_AVAILABLE, "You must be logged in to create an item.", HttpStatus.UNAUTHORIZED);
//        }

        // Check for a valid item
        Item feedbackItem = itemDAO.findByID(itemId);
        if (feedback == null){
            throw new RestException(NOT_AVAILABLE, "The requested item does not exist.", HttpStatus.OK);
        }

        // Check that content was set
        if (feedback.getContent() == null){
            throw new RestException("Malformed request.", "A required request parameter 'content' was missing.", HttpStatus.BAD_REQUEST);
        }

        // Auction should be over
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        if (currentTimestamp.before(feedbackItem.getEndtime())){
            throw new RestException(NOT_AVAILABLE, "You are not allowed to provide feedback for this item yet.", HttpStatus.FORBIDDEN);
        }

        // Winning Bidder is the last highest bidder for a closed auction
        Bidding winningBid = biddingDAO.getHighestBiddingForItem(feedbackItem.getId());
        Userr bidWinner = winningBid.getUserr();

        // Only the winning bidder is authorized to give feedback
//        if (!auth.getName().equals(bidWinner.getEmail())){
//            throw new RestException(NOT_AVAILABLE, "You are not authorized to provide feedback for this item.", HttpStatus.UNAUTHORIZED);
//        }

        // Set timestamp to current time
        feedback.setTimestamp(currentTimestamp);
        feedback.setItem(feedbackItem);

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
        if (feedbacks == null || feedbacks.isEmpty()){
            throw new RestException(NOT_AVAILABLE, "No feedback found.", HttpStatus.OK);
        }
        return feedbacks;
    }

    @Override
    public List<Feedback> findAllByUserrId(Long userrId) {

        Userr requestUserr = userrDao.getUserrByID(userrId);
        if (requestUserr == null){
            throw new RestException(NOT_AVAILABLE, "The requested user does not exist.", HttpStatus.OK);
        }

        List<Feedback> feedbacks = feedbackDAO.findAllByUserrId(userrId);
        if (feedbacks == null || feedbacks.isEmpty()){
            throw new RestException(NOT_AVAILABLE, "No feedback found for this user.", HttpStatus.OK);
        }

        return feedbacks;
    }

    @Override
    public List<Feedback> findAllByItemId(Long itemId) {

        Item requestItem = itemDAO.findByID(itemId);
        if (requestItem == null) {
            throw new RestException(NOT_AVAILABLE, "The requested item does not exist.", HttpStatus.OK);
        }

        List<Feedback> feedbacks = feedbackDAO.findAllByItemId(itemId);
        if (feedbacks == null || feedbacks.isEmpty()) {
            throw new RestException(NOT_AVAILABLE, "No feedback found for this item", HttpStatus.OK);
        }
        return feedbacks;
    }
}
