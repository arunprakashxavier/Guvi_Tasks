package com.project.busapp.repository;

import com.project.busapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email); // Find user by email
    Boolean existsByEmail(String email); // Check if a user with the given email exists

}