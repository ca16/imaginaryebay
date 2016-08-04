package com.imaginaryebay.Repository;

import com.imaginaryebay.Models.CommentThread;

/**
 * Created by Brian on 8/1/2016.
 */
public interface CommentThreadRepository {

    public CommentThread getFeedbackForItem(Long itemId);

}
