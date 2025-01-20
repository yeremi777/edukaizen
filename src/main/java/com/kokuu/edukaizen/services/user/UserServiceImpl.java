package com.kokuu.edukaizen.services.user;

import org.springframework.stereotype.Service;

import com.kokuu.edukaizen.common.Password;
import com.kokuu.edukaizen.common.enums.UserRole;
import com.kokuu.edukaizen.dao.UserRepository;
import com.kokuu.edukaizen.entities.User;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {
    private final EntityManager entityManager;
    private final UserRepository userRepository;
    private final Password password;

    public UserServiceImpl(EntityManager entityManager, UserRepository userRepository, Password password) {
        this.entityManager = entityManager;
        this.userRepository = userRepository;
        this.password = password;
    }

    @Override
    @Transactional
    public void save(User user) {
        user.setPassword(password.encryptPassword(user.getPassword()));
        user.setRoleId(UserRole.STUDENT.getValue());

        userRepository.save(user);

        entityManager.flush();
        entityManager.refresh(user);
    }

    @Override
    public User findUserByIdentity(String value) {
        User user = userRepository.findUserByIdentity(value);

        return user;
    }

    @Override
    public User findUserByIdentity(String username, String email) {
        User user = userRepository.findUserByIdentity(username, email);

        return user;
    }
}
