package com.example.books_tracker.service;

import com.example.books_tracker.model.Authors;
import com.example.books_tracker.model.Genres;
import com.example.books_tracker.repository.GenresRepository;
import com.example.books_tracker.specifications.GenresSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GenresService {

    private final GenresRepository genresRepository;

    public GenresService(GenresRepository genresRepository) {
        this.genresRepository = genresRepository;
    }

    public Genres getGenre(Long id) {
        return genresRepository.findById(id).get();
    }

    public List<Genres> getAllGenres() {
        return genresRepository.findAll();
    }

    public Page<Genres> getAllGenres(String search, Pageable pageable) {
        return genresRepository.findAll(GenresSpecification.findGenresSpecification(search), pageable);
    }

    public void createGenre(String genres) {
        Genres genre = new Genres();
        genre.setName(genres);
        genre.setGenresId(null);
        genresRepository.save(genre);
    }

    public void updateGenre(Long id, String genres) {
        Genres genre = genresRepository.findById(id).get();
        genre.setName(genres);
        genresRepository.save(genre);
    }

    public void deleteGenre(Long id) {
        genresRepository.deleteById(id);
    }
}
