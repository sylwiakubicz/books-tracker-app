package com.example.books_tracker.repository;

import com.example.books_tracker.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long>, JpaSpecificationExecutor<Users> {
    Optional<Users> findByUserId(Long id);
    Optional<Users> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    @Query("SELECT COUNT(u) FROM Users u")
    Long countTotalUsers();

    @Query("SELECT COUNT(u) FROM Users u WHERE u.role.role = 'ADMIN_ROLE'")
    Long countAdminUsers();

    @Query("SELECT COUNT(u) FROM Users u WHERE u.role.role = 'USER_ROLE'")
    Long countUserUsers();
}
