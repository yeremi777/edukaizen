package com.kokuu.edukaizen.services.auth;

import com.kokuu.edukaizen.dto.auth.LoginDTO;
import com.kokuu.edukaizen.dto.auth.RegisterDTO;

public interface AuthService {
    void register(RegisterDTO input);

    void login(LoginDTO input);
}
