package com.imaginaryebay.DAO;

import com.imaginaryebay.Models.Feedback;
import com.imaginaryebay.Models.Userr;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Brian on 8/2/2016.
 */
public class FeedbackDAOImpl implements FeedbackDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public void persist(Feedback feedback) {
        entityManager.persist(feedback);
    }

    @Override
    public void merge(Feedback feedback) {
        entityManager.merge(feedback);
    }

    @Override
    public void refresh(Feedback feedback) {
        entityManager.refresh(feedback);
    }

    @Override
    public Feedback find(Feedback feedback) {
        return entityManager.find(Feedback.class, feedback.getId());
    }

    @Override
    public Feedback findById(Long id) {
        return entityManager.find(Feedback.class, id);
    }

    @Override
    public List<Feedback> findAll() {
        Query query = entityManager.createQuery("SELECT f FROM Feedback f");
        return query.getResultList();
    }

    @Override
    public List<Feedback> findAllByUserrId(Long userId) {
        Query query = entityManager.createQuery("SELECT f FROM Feedback f JOIN f.item i WHERE i.userr.id = ?1");
        query.setParameter(1, userId);
        return query.getResultList();
    }

    @Override
    public List<Feedback> findAllByItemId(Long itemId) {
        Query query = entityManager.createQuery("SELECT f FROM Feedback f WHERE f.item.id = ?1");
        query.setParameter(1, itemId);
        return query.getResultList();
    }

}
