package com.authentication.repository;

import com.authentication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    // If you want to return the whole user object:
    User findByUsername(String username);

    User findByEmail(String email);
}
