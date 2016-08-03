package com.imaginaryebay.DAO;

import com.imaginaryebay.Models.Feedback;

import java.util.List;

/**
 * Created by Brian on 8/2/2016.
 */
public interface FeedbackDAO {

    void persist(Feedback feedback);

    void merge(Feedback feedback);

    void refresh(Feedback feedback);

    Feedback find(Feedback feedback);

    Feedback findById(Long id);

    List<Feedback> findAll();

    List<Feedback> findAllByUserrId(Long userrId);

    List<Feedback> findAllByItemId(Long itemId);
}
