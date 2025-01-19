package com.kokuu.edukaizen.services.user;

import org.springframework.stereotype.Service;

import com.kokuu.edukaizen.common.Password;
import com.kokuu.edukaizen.common.enums.UserRole;
import com.kokuu.edukaizen.dao.UserRepository;
import com.kokuu.edukaizen.entities.User;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final Password password;

    public UserServiceImpl(UserRepository userRepository, Password password) {
        this.userRepository = userRepository;
        this.password = password;
    }

    @Override
    @Transactional
    public void save(User user) {
        user.setPassword(password.encryptPassword(user.getPassword()));
        user.setRoleId(UserRole.Student.getValue());

        userRepository.save(user);
    }

    @Override
    public User findUserByIdentity(String value) {
        User user = userRepository.findUserByIdentity(value);

        return user;
    }
}
