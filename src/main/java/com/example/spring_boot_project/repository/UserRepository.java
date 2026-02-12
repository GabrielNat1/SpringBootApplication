package com.example.spring_boot_project.repository;

import com.example.spring_boot_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByPublicId(String publicId);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
