package com.example.books_tracker.controller;

import com.example.books_tracker.DTO.AddOrUpdateBookStateDTO;
import com.example.books_tracker.model.BookStates;
import com.example.books_tracker.model.Users;
import com.example.books_tracker.repository.UserRepository;
import com.example.books_tracker.service.BookStatesService;
import com.example.books_tracker.service.UserService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/bookstate")
public class BookStatesController {

    private final BookStatesService bookStatesService;
    private final UserService userService;
    private final UserRepository userRepository;

    public BookStatesController(BookStatesService bookStatesService, UserService userService, UserRepository userRepository) {
        this.bookStatesService = bookStatesService;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<Page<BookStates>> getAll(@RequestParam(required = false) String status,
                                                   @RequestParam(required = false) Integer rate,
                                                   @RequestParam Long user_id,
                                                   @PageableDefault @ParameterObject Pageable pageable) {

        Page<BookStates> userBookStates = bookStatesService.getAll(user_id, status, rate, pageable);
        return new ResponseEntity<>(userBookStates, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookStates> getBookState(@PathVariable Long id, @AuthenticationPrincipal User user) {
        if (user.getUsername().equals(bookStatesService.getBookState(id).getUserID().getUsername())) {
            BookStates bookState = bookStatesService.getBookState(id);
            return new ResponseEntity<>(bookState, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/exist/{book_id}")
    public ResponseEntity<BookStates> existBook(@PathVariable Long book_id, @AuthenticationPrincipal User user) {
        Users userObj = userRepository.findByUsername(user.getUsername()).orElse(null);
        BookStates bookState = bookStatesService.checkIfExistAndGet(book_id, userObj);
        return new ResponseEntity<>(bookState, HttpStatus.OK);
    }

    @PostMapping("/create/{book_id}")
    public ResponseEntity<BookStates> createBookState(@PathVariable Long book_id, @RequestBody AddOrUpdateBookStateDTO stateData, @AuthenticationPrincipal User user) {
        BookStates bookState = bookStatesService.addToStatus(book_id, user.getUsername(), stateData);
        return new ResponseEntity<>(bookState, HttpStatus.OK);
    }

    @PutMapping("/update/{book_id}")
    public ResponseEntity<BookStates> updateBookState(@PathVariable Long book_id, @RequestBody AddOrUpdateBookStateDTO stateData, @AuthenticationPrincipal User user) {
        BookStates bookState = bookStatesService.updateToStatus(book_id, user.getUsername(), stateData);
        return new ResponseEntity<>(bookState, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBookStatus(@PathVariable Long id, @AuthenticationPrincipal User user) {
        if (user.getUsername().equals(bookStatesService.getBookState(id).getUserID().getUsername())) {
            bookStatesService.deleteBookState(id);
            return new ResponseEntity<>("Book removed correctly", HttpStatus.OK);
        }
        return new ResponseEntity<>("Not permitted to delete this entity", HttpStatus.BAD_REQUEST);
    }
}
