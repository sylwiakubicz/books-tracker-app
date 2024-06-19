package com.example.books_tracker.repository;

import com.example.books_tracker.model.Genres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenresRepository extends JpaRepository<Genres, Long> {
    Optional<Genres> findByName(String name);
}
