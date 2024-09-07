package com.example.books_tracker.repository;

import com.example.books_tracker.model.Authors;
import com.example.books_tracker.model.BookStates;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorsRepository extends JpaRepository<Authors, Long>, JpaSpecificationExecutor<Authors> {
    Optional<Authors> findByNameAndSurname(String name, String surname);

}
