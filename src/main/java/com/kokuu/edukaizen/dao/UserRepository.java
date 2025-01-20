package com.kokuu.edukaizen.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kokuu.edukaizen.entities.User;

public interface UserRepository extends JpaRepository<User, Integer>, UserDAO {
    Optional<User> findByIdAndEmail(Long id, String email);
}
