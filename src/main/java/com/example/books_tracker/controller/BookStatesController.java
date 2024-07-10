package com.example.books_tracker.controller;

import com.example.books_tracker.DTO.AddBookToInProgressStatusDTO;
import com.example.books_tracker.DTO.AddBookToReadStatusDTO;
import com.example.books_tracker.model.BookStates;
import com.example.books_tracker.service.BookStatesService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.Objects;


@RestController
@RequestMapping("/bookstate")
public class BookStatesController {

    private final BookStatesService bookStatesService;

    public BookStatesController(BookStatesService bookStatesService) {
        this.bookStatesService = bookStatesService;
    }

    @GetMapping
    public ResponseEntity<Page<BookStates>> getAll(@RequestParam(required = false) String status,
                                                   @RequestParam(required = false) Integer rate,
                                                   @PageableDefault @ParameterObject Pageable pageable,
                                                   @AuthenticationPrincipal User user) {

        Page<BookStates> userBookStates = bookStatesService.getAll(status, rate, pageable);
        return new ResponseEntity<>(userBookStates, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookStates> getBookState(@PathVariable Long id, @AuthenticationPrincipal User user) {
        BookStates bookState = bookStatesService.getBookState(id);
        return new ResponseEntity<>(bookState, HttpStatus.OK);
    }

    @PostMapping("/{book_id}")
    public ResponseEntity<String> addBookToToReadStatus(@PathVariable Long book_id, @AuthenticationPrincipal User user) {
        bookStatesService.addToToReadStatus(book_id);
        return new ResponseEntity<>("Book added to 'to read' status", HttpStatus.OK);
    }

    @PostMapping("/inprogress/{book_id}")
    public ResponseEntity<String> addBookToInProgressStatus(@PathVariable Long book_id,
                                                            @RequestBody AddBookToInProgressStatusDTO bookStatusData,
                                                            @AuthenticationPrincipal User user) {
        if (Objects.equals(bookStatesService.addToInProgressStatus(book_id, bookStatusData), "exist")) {
            return new ResponseEntity<>("Book elready in some status", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Book added to 'in progress' status", HttpStatus.OK);
    }

    @PutMapping("/inprogress/{book_id}")
    public ResponseEntity<String> moveBookToInProgressStatus(@PathVariable Long book_id,
                                                            @RequestBody AddBookToInProgressStatusDTO bookStatusData,
                                                             @AuthenticationPrincipal User user) {
        bookStatesService.moveToInProgressStatus(book_id, bookStatusData);
        return new ResponseEntity<>("Book moved to 'in progress' status", HttpStatus.OK);
    }

    @PostMapping("/read/{book_id}")
    public ResponseEntity<String> addBookToReadStatus(@PathVariable Long book_id,
                                                      @RequestBody AddBookToReadStatusDTO bookStatusData,
                                                      @AuthenticationPrincipal User user) {
        if (Objects.equals(bookStatesService.addBookToReadStatus(book_id, bookStatusData), "exist")) {
            return new ResponseEntity<>("Book already in some status", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Book added to 'read' status", HttpStatus.OK);
    }

    @PutMapping("/read/{book_id}")
    public ResponseEntity<String> moveBookToReadStatus(@PathVariable Long book_id,
                                                       @RequestBody AddBookToReadStatusDTO bookStatusData,
                                                       @AuthenticationPrincipal User user) {
        bookStatesService.moveBookToReadStatus(book_id, bookStatusData);
        return new ResponseEntity<>("Book moved to 'read' status", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBookStatus(@PathVariable Long id, @AuthenticationPrincipal User user) {
        bookStatesService.deleteBookState(id);
        return new ResponseEntity<>("Book removed correctly", HttpStatus.OK);
    }

}
