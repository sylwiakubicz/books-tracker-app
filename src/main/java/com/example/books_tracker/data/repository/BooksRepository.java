package com.example.books_tracker.data.repository;

import com.example.books_tracker.data.entities.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepository extends JpaRepository<Books, Long>, JpaSpecificationExecutor<Books> {
}
