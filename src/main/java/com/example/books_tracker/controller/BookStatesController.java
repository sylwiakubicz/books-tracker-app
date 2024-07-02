package com.example.books_tracker.controller;

import com.example.books_tracker.model.BookStates;
import com.example.books_tracker.service.BookStatesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/bookstate")
public class BookStatesController {

    private final BookStatesService bookStatesService;

    public BookStatesController(BookStatesService bookStatesService) {
        this.bookStatesService = bookStatesService;
    }

    @PostMapping("/{book_id}")
    public ResponseEntity<String> addBookToToReadStatus(@PathVariable Long book_id) {
        bookStatesService.addToToReadStatus(book_id);
        return new ResponseEntity<>("Book added to to read status", HttpStatus.OK);
    }
}
