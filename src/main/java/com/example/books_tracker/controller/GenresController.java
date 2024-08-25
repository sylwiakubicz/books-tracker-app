package com.example.books_tracker.controller;

import com.example.books_tracker.model.Genres;
import com.example.books_tracker.service.GenresService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenresController {

    private final GenresService genresService;

    public GenresController(GenresService genresService) {
        this.genresService = genresService;
    }

    @GetMapping
    public ResponseEntity<List<Genres>> getGenres() {
        return new ResponseEntity<>(genresService.getAllGenres(), HttpStatus.OK);
    }
}
