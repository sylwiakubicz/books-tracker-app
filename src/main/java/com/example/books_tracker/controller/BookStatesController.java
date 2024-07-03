package com.example.books_tracker.controller;

import com.example.books_tracker.model.BookStates;
import com.example.books_tracker.model.Statuses;
import com.example.books_tracker.repository.BookStateRepository;
import com.example.books_tracker.repository.StatusesRepository;
import com.example.books_tracker.service.BookStatesService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/bookstate")
public class BookStatesController {

    private final BookStatesService bookStatesService;
    private final BookStateRepository bookStateRepository;
    private final StatusesRepository statusesRepository;

    public BookStatesController(BookStatesService bookStatesService, BookStateRepository bookStateRepository, StatusesRepository statusesRepository) {
        this.bookStatesService = bookStatesService;
        this.bookStateRepository = bookStateRepository;
        this.statusesRepository = statusesRepository;
    }

    @GetMapping
    public ResponseEntity<Page<BookStates>> getAll(@RequestParam(required = false) String status,
                                                   @RequestParam(required = false) Integer rate,
                                                   @PageableDefault @ParameterObject Pageable pageable) {

        Page<BookStates> userBookStates = bookStatesService.getAll(status, rate, pageable);
        return new ResponseEntity<>(userBookStates, HttpStatus.OK);
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

    @PutMapping("/{book_status_id}")
    public ResponseEntity<String> moveBookToReadStatus(@PathVariable Long book_status_id,
                                                       @RequestParam Integer rate) {
        BookStates bookState = bookStateRepository.findById(book_status_id).orElseThrow();
        if (!Objects.equals(bookState.getStatus().getStatusName(), "in progress")) {
            return new ResponseEntity<>("Firstly add book to 'in progress' status", HttpStatus.BAD_REQUEST);
        }

        bookStatesService.moveBookToReadStatus(bookState, rate);
        return new ResponseEntity<>("Book added to 'read' status", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delteBookStatus(@PathVariable Long id) {
        bookStatesService.deleteBookState(id);
        return new ResponseEntity<>("Book removed corectly", HttpStatus.OK);
    }

}
