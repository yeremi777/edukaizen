package com.kokuu.edukaizen.dao;

import com.kokuu.edukaizen.entities.User;

public interface UserDAO {
    User findUserByIdentity(String value);
}
