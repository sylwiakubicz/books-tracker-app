package com.example.books_tracker.repository;

import com.example.books_tracker.model.BookStates;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookStateRepository extends JpaRepository<BookStates, Long> {
}
