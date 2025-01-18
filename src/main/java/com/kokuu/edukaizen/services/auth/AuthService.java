package com.kokuu.edukaizen.services.auth;

import com.kokuu.edukaizen.dto.auth.RegisterDTO;
import com.kokuu.edukaizen.entities.User;

public interface AuthService {
    void register(RegisterDTO input);

    User findUserByIdentity(String value);
}
