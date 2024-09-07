package com.example.books_tracker.controller;

import com.example.books_tracker.DTO.AuthorDTO;
import com.example.books_tracker.model.Authors;
import com.example.books_tracker.service.AuthorsService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/authors")
public class AuthorsController {

    private final AuthorsService authorsService;

    public AuthorsController(AuthorsService authorsService) {
        this.authorsService = authorsService;
    }

    @GetMapping
    public ResponseEntity<Page<Authors>> getAuthors(@RequestParam(required = false) String search,
                                                  @PageableDefault @ParameterObject Pageable pageable) {
        return new ResponseEntity<>(authorsService.getAllAuthors(search,pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Authors> getAuthorsById(@PathVariable Long id) {
        return new ResponseEntity<>(authorsService.getAuthorsById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addAuthors(@RequestBody AuthorDTO authorDTO) {
        authorsService.save(authorDTO.getAuthorsName(), authorDTO.getAuthorsSurname());
        return new ResponseEntity<>(Map.of("message", "Author added"), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateAuthors(@RequestBody Authors authors) {
        authorsService.update(authors);
        return new ResponseEntity<>(Map.of("message", "Author updated"), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuthors(@PathVariable Long id) {
        authorsService.deleteById(id);
        return new ResponseEntity<>(Map.of("message", "Author deleted"), HttpStatus.OK);
    }
}
