package com.example.books_tracker.controller;

import com.example.books_tracker.model.Authors;
import com.example.books_tracker.service.AuthorsService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public List<Authors> getAuthors(@RequestParam(required = false) String search,
                                    @PageableDefault @ParameterObject Pageable pageable) {
        return authorsService.getAllAuthors(search, pageable);
    }
}
