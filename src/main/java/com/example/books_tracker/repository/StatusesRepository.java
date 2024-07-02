package com.example.books_tracker.repository;

import com.example.books_tracker.model.Statuses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusesRepository extends JpaRepository<Statuses, Long> {
    Optional<Statuses> findStatusesByStatusName(String statusName);
}

