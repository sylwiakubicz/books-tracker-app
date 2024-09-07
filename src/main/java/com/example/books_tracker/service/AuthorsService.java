package com.example.books_tracker.service;

import com.example.books_tracker.model.Authors;
import com.example.books_tracker.repository.AuthorsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.Optional;

import static com.example.books_tracker.specifications.AuthorsSpecification.findAuthorsSpecification;

@Service
public class AuthorsService {
    private final AuthorsRepository authorsRepository;

    public AuthorsService(AuthorsRepository authorsRepository) {
        this.authorsRepository = authorsRepository;
    }

    public Page<Authors> getAllAuthors(String search, Pageable pageable) {
        return authorsRepository.findAll(findAuthorsSpecification(search), pageable);
    }

    public Authors getAuthorsById(Long id) {
        return authorsRepository.findById(id).orElse(null);
    }

    public void save(String name, String surname) {
        Optional<Authors> existingAuthor = authorsRepository.findByNameAndSurname(name, surname);

        if (existingAuthor.isEmpty()) {
            Authors author = new Authors();
            author.setAuthorId(null);
            author.setName(name);
            author.setSurname(surname);
            authorsRepository.save(author);
        }
    }

    public void update(Authors author) {
        authorsRepository.save(author);
    }

    public void deleteById(Long id) {
        authorsRepository.deleteById(id);
    }
}
