package com.imaginaryebay.DAO;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.imaginaryebay.Models.Message;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;

@Repository
@Transactional
public class MessageDaoImpl implements  MessageDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public void persist(Message message) {
        this.entityManager.persist(message);

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Message> findAllMessagesByReceiverID(Long receiver_id) {
        Query query = this.entityManager.createQuery(
                "select m from Message m where m.receiver_id.id = ?1");
        query.setParameter(1, receiver_id);
        return query.getResultList();
    }
}