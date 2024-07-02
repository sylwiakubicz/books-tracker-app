package com.example.books_tracker.service;

import com.example.books_tracker.model.BookStates;
import com.example.books_tracker.model.Books;
import com.example.books_tracker.model.Statuses;
import com.example.books_tracker.model.Users;
import com.example.books_tracker.repository.BookStateRepository;
import com.example.books_tracker.repository.BooksRepository;
import com.example.books_tracker.repository.StatusesRepository;
import com.example.books_tracker.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class BookStatesService {
    private final BookStateRepository bookStateRepository;

    private final BooksRepository booksRepository;
    private final StatusesRepository statusesRepository;
    private final UserRepository userRepository;

    public BookStatesService(BookStateRepository bookStateRepository, BooksRepository booksRepository, StatusesRepository statusesRepository, UserRepository userRepository) {
        this.bookStateRepository = bookStateRepository;
        this.booksRepository = booksRepository;
        this.statusesRepository = statusesRepository;
        this.userRepository = userRepository;
    }

    public void addToToReadStatus(Long bookId) {
        BookStates bookState = new BookStates();
        Books book = booksRepository.findById(bookId).orElseThrow();
        Statuses status = statusesRepository.findStatusesByStatusName("to read").orElseThrow();

        // chwilowe rozwiąznie z id użytkonika
        Users user = userRepository.findByUserId(1L).orElseThrow();

        bookState.setBook(book);
        bookState.setStatus(status);
        bookState.setUserID(user);
        bookStateRepository.save(bookState);
    }

    public void addToInProgressStatus(Long bookId) {
        BookStates bookState = new BookStates();
        Books book = booksRepository.findById(bookId).orElseThrow();
        Statuses status = statusesRepository.findStatusesByStatusName("in progress").orElseThrow();
        Instant date = Instant.now();

        // chwilowe rozwiąznie z id użytkonika
        Users user = userRepository.findByUserId(1L).orElseThrow();

        bookState.setBook(book);
        bookState.setStatus(status);
        bookState.setUserID(user);
        bookState.setStartDate(date);
        bookState.setCurrentPage(0);
        bookStateRepository.save(bookState);
    }
}
