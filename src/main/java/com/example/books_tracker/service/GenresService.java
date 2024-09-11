package com.example.books_tracker.service;

import com.example.books_tracker.model.Authors;
import com.example.books_tracker.model.Genres;
import com.example.books_tracker.repository.GenresRepository;
import com.example.books_tracker.specifications.GenresSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.books_tracker.specifications.AuthorsSpecification.findAuthorsSpecification;
import static com.example.books_tracker.specifications.GenresSpecification.findGenresSpecification;

@Service
public class GenresService {

    private final GenresRepository genresRepository;

    public GenresService(GenresRepository genresRepository) {
        this.genresRepository = genresRepository;
    }

    public List<Genres> getAllGenres() {
        return genresRepository.findAll();
    }

    public Page<Genres> getAllGenres(String search, Pageable pageable) {
        return genresRepository.findAll(GenresSpecification.findGenresSpecification(search), pageable);
    }
}
