package com.example.books_tracker.repository;

import com.example.books_tracker.model.BookStates;
import com.example.books_tracker.model.Books;
import com.example.books_tracker.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;


public interface BookStateRepository extends JpaRepository<BookStates, Long>, JpaSpecificationExecutor<BookStates> {
    Optional<BookStates> findBookStatesByBookAndUserID(Books book, Users user);

    Boolean existsByBook_BookIdAndUserID_UserId(Long bookId, Long userId);
    Optional<BookStates> findByBook_BookIdAndUserID_UserId(Long bookId, Long userId);
}
