package com.example.books_tracker.controller;

import com.example.books_tracker.model.Authors;
import com.example.books_tracker.model.Books;
import com.example.books_tracker.service.AuthorsService;
import com.example.books_tracker.specifications.AuthorsSpecification;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    public ResponseEntity<String> addAuthors(@RequestBody String authorsName,
                                             @RequestBody String authorsSurname) {
        authorsService.save(authorsName, authorsSurname);
        return new ResponseEntity<>("Author created successfully", HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<String> updateAuthors(@RequestBody Authors authors) {
        authorsService.update(authors);
        return new ResponseEntity<>("Author created successfully", HttpStatus.OK);
    }
}
