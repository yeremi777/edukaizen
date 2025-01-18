package com.kokuu.edukaizen.services.auth;

import org.springframework.stereotype.Service;

import com.kokuu.edukaizen.common.Password;
import com.kokuu.edukaizen.common.enums.UserRole;
import com.kokuu.edukaizen.dao.UserRepository;
import com.kokuu.edukaizen.dto.auth.RegisterDTO;
import com.kokuu.edukaizen.entities.User;

import jakarta.transaction.Transactional;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final Password password;

    public AuthServiceImpl(UserRepository userRepository, Password password) {
        this.userRepository = userRepository;
        this.password = password;
    }

    @Override
    @Transactional
    public void register(RegisterDTO input) {
        User user = new User(
                input.firstName(),
                input.lastName(),
                input.username(),
                input.email(),
                password.encryptPassword(input.password()));

        user.setRoleId(UserRole.Student.getValue());

        userRepository.save(user);
    }

    @Override
    public User findUserByIdentity(String value) {
        User user = userRepository.findUserByIdentity(value);

        return user;
    }
}
