package com.example.books_tracker.repository;

import com.example.books_tracker.model.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<UserRoles, Long> {
    Optional<UserRoles> findByRole(String name);
}
