package com.kokuu.edukaizen.dao;

import org.springframework.stereotype.Repository;

import com.kokuu.edukaizen.entities.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
public class UserDAOImpl implements UserDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User findUserByIdentity(String value) {
        User user = null;

        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u WHERE u.username = :value OR u.email = :value",
                User.class);

        query.setParameter("value", value);

        try {
            user = query.getSingleResult();
        } catch (NoResultException e) {
            user = null;
        }

        return user;
    }
}
