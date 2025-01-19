package com.kokuu.edukaizen.services.user;

import com.kokuu.edukaizen.entities.User;

public interface UserService {
    void save(User user);

    User findUserByIdentity(String value);
}
