package com.example.books_tracker.repository;

import com.example.books_tracker.model.Authors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorsRepository extends JpaRepository<Authors, Long> {
    Optional<Authors> findByNameAndSurname(String name, String surname);
}
