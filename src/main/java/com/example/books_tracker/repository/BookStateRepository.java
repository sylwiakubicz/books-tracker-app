package com.example.books_tracker.repository;

import com.example.books_tracker.model.BookStates;
import com.example.books_tracker.model.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface BookStateRepository extends JpaRepository<BookStates, Long>, JpaSpecificationExecutor<BookStates> {
}
