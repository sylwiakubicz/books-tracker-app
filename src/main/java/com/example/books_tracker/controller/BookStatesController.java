package com.example.books_tracker.controller;

import com.example.books_tracker.service.BookStatesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
        return new ResponseEntity<>("Book added to 'to read' status", HttpStatus.OK);
    }

    @PostMapping("/inprogress/{book_id}")
    public ResponseEntity<String> addBookToInProgressStatus(@PathVariable Long book_id) {
        bookStatesService.addToInProgressStatus(book_id);
        return new ResponseEntity<>("Book added to 'in progress' status", HttpStatus.OK);
    }

}
