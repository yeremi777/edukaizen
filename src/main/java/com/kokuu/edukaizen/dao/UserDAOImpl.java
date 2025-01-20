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
                "SELECT u FROM User u JOIN FETCH u.role WHERE u.username = :value OR u.email = :value",
                User.class);

        query.setParameter("value", value);

        try {
            user = query.getSingleResult();
        } catch (NoResultException e) {
            user = null;
        }

        return user;
    }

    @Override
    public User findUserByIdentity(String username, String email) {
        User user = null;

        TypedQuery<User> query = entityManager.createQuery(
                "SELECT u FROM User u JOIN FETCH u.role WHERE u.username = :username OR u.email = :email",
                User.class);

        query.setParameter("username", username);
        query.setParameter("email", email);

        try {
            user = query.getSingleResult();
        } catch (NoResultException e) {
            user = null;
        }

        return user;
    }
}
