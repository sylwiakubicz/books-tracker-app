package com.example.books_tracker.service;

import com.example.books_tracker.model.Authors;
import com.example.books_tracker.repository.AuthorsRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.books_tracker.specifications.AuthorsSpecification.findAuthorsSpecification;

@Service
public class AuthorsService {
    private final AuthorsRepository authorsRepository;

    public AuthorsService(AuthorsRepository authorsRepository) {
        this.authorsRepository = authorsRepository;
    }

    public List<Authors> getAllAuthors(String search, Pageable pageable) {
        return authorsRepository.findAll(findAuthorsSpecification(search), pageable);
    }
}
