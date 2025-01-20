package com.kokuu.edukaizen.common;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.kokuu.edukaizen.entities.User;

public class UserInfoDetails implements UserDetails {
    private final User user;

    public UserInfoDetails(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections
                .singletonList(new SimpleGrantedAuthority("ROLE_" + this.user.getRole().getName().toUpperCase()));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.user.getEmail();
    }
}
