package com.example.books_tracker.controller;

import com.example.books_tracker.DTO.AddBookToInProgressStatusDTO;
import com.example.books_tracker.DTO.AddBookToReadStatusDTO;
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

    @GetMapping("/{id}")
    public ResponseEntity<BookStates> getBookState(@PathVariable Long id) {
        BookStates bookState = bookStatesService.getBookState(id);
        return new ResponseEntity<>(bookState, HttpStatus.OK);
    }

    @PostMapping("/{book_id}")
    public ResponseEntity<String> addBookToToReadStatus(@PathVariable Long book_id) {
        bookStatesService.addToToReadStatus(book_id);
        return new ResponseEntity<>("Book added to 'to read' status", HttpStatus.OK);
    }

    @PostMapping("/inprogress/{book_id}")
    public ResponseEntity<String> addBookToInProgressStatus(@PathVariable Long book_id,
                                                            @RequestBody AddBookToInProgressStatusDTO bookStatusData) {
        bookStatesService.addToInProgressStatus(book_id, bookStatusData);
        return new ResponseEntity<>("Book added to 'in progress' status", HttpStatus.OK);
    }

    @PutMapping("/inprogress/{book_id}")
    public ResponseEntity<String> moveBookToInProgressStatus(@PathVariable Long book_id,
                                                            @RequestBody AddBookToInProgressStatusDTO bookStatusData) {
        bookStatesService.moveToInProgressStatus(book_id, bookStatusData);
        return new ResponseEntity<>("Book moved to 'in progress' status", HttpStatus.OK);
    }

    @PostMapping("/read/{book_id}")
    public ResponseEntity<String> addBookToReadStatus(@PathVariable Long book_id,
                                                       @RequestBody AddBookToReadStatusDTO bookStatusData) {
        bookStatesService.addBookToReadStatus(book_id, bookStatusData);
        return new ResponseEntity<>("Book added to 'read' status", HttpStatus.OK);
    }

    @PutMapping("/read/{book_id}")
    public ResponseEntity<String> moveBookToReadStatus(@PathVariable Long book_id,
                                                       @RequestBody AddBookToReadStatusDTO bookStatusData) {
        bookStatesService.moveBookToReadStatus(book_id, bookStatusData);
        return new ResponseEntity<>("Book moved to 'read' status", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBookStatus(@PathVariable Long id) {
        bookStatesService.deleteBookState(id);
        return new ResponseEntity<>("Book removed corectly", HttpStatus.OK);
    }

}
