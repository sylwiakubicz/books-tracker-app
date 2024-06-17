package com.example.books_tracker.data.repository;

import com.example.books_tracker.data.entities.Genres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenresRepository extends JpaRepository<Genres, Long> {
    Optional<Genres> findByName(String name);
}
