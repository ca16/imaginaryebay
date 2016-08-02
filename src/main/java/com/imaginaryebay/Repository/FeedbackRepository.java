package com.imaginaryebay.Repository;

import com.imaginaryebay.Models.Feedback;

/**
 * Created by Brian on 8/1/2016.
 */
public interface FeedbackRepository {

    public Feedback getFeedbackForItem(Long itemId);

}
