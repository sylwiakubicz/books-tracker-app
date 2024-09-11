package com.example.books_tracker.controller;

import com.example.books_tracker.DTO.GenreDTO;
import com.example.books_tracker.model.Genres;
import com.example.books_tracker.repository.GenresRepository;
import com.example.books_tracker.service.GenresService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/genres")
public class GenresController {

    private final GenresService genresService;
    private final GenresRepository genresRepository;

    public GenresController(GenresService genresService, GenresRepository genresRepository) {
        this.genresService = genresService;
        this.genresRepository = genresRepository;
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

    @PostMapping
    public ResponseEntity<?> addGenres(@RequestBody GenreDTO genreDTO) {
        String genres = genreDTO.getGenres();
        if (genresRepository.findByName(genres).isEmpty()) {
            genresService.createGenre(genres);
            return new ResponseEntity<>(Map.of("message", "Genre added"), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(Map.of("message", "Genre already exists"), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateGenres(@PathVariable Long id, @RequestBody GenreDTO genreDTO) {
        String genres = genreDTO.getGenres();
        genresService.updateGenre(id, genres);
        return new ResponseEntity<>(Map.of("message", "Genre updated"), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGenres(@PathVariable Long id) {
        genresService.deleteGenre(id);
        return new ResponseEntity<>(Map.of("message", "Genre deleted"), HttpStatus.OK);
    }
}
