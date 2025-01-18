package com.kokuu.edukaizen.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Password {
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public Password(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String encryptPassword(String value) {
        return passwordEncoder.encode(value);
    }

    public boolean verifyPassword(String plainText, String hashed) {
        return passwordEncoder.matches(plainText, hashed);
    }
}
