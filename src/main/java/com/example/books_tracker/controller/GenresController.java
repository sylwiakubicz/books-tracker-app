package com.example.books_tracker.controller;

import com.example.books_tracker.model.Genres;
import com.example.books_tracker.service.GenresService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/pagable")
    public ResponseEntity<Page<Genres>> getGenres(@RequestParam(required = false) String search,
                                                                                  @PageableDefault @ParameterObject Pageable pageable) {
        Page<Genres> genresPage = genresService.getAllGenres(search, pageable);
        return new ResponseEntity<>(genresPage, HttpStatus.OK);
    }
}
