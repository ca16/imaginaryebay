package com.imaginaryebay.Repository;

import com.imaginaryebay.Models.Feedback;

import java.util.List;

/**
 * Created by Brian on 8/2/2016.
 */
public interface FeedbackRepository {

    Feedback save(Feedback feedback);

    Feedback findById(Long id);

    List<Feedback> findAll();

    List<Feedback> findAllByUserrId(Long id);

    List<Feedback> findAllByItemId(Long itemId);

}
