package com.example.books_tracker.controller;

import com.example.books_tracker.model.Books;
import com.example.books_tracker.service.BooksService;
import jakarta.persistence.GeneratedValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BooksController {

    private final BooksService booksService;

    public BooksController(BooksService booksService) {
        this.booksService = booksService;
    }

    @GetMapping
    public List<Books> list() {
        return booksService.list();
    }

    @GetMapping("/{id}")
    public Books get(@PathVariable Long id) {
        return booksService.get(id);
    }

    @PostMapping
    public Books create(@RequestBody Books books) {
        return booksService.create(books);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        booksService.delete(id);
    }

}
