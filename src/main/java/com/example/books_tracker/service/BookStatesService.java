package com.example.books_tracker.service;

import com.example.books_tracker.DTO.AddBookToInProgressStatusDTO;
import com.example.books_tracker.DTO.AddBookToReadStatusDTO;
import com.example.books_tracker.model.BookStates;
import com.example.books_tracker.model.Books;
import com.example.books_tracker.model.Statuses;
import com.example.books_tracker.model.Users;
import com.example.books_tracker.repository.BookStateRepository;
import com.example.books_tracker.repository.BooksRepository;
import com.example.books_tracker.repository.StatusesRepository;
import com.example.books_tracker.repository.UserRepository;
import com.example.books_tracker.specifications.BookStatesSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

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

    public Page<BookStates> getAll(String status, Integer rate, Pageable pageable) {
        if (status != null) {
            Statuses statusObject = statusesRepository.findStatusesByStatusName(status).orElseThrow();
            return bookStateRepository.findAll(BookStatesSpecification.findBookStatesSpecification(statusObject,rate), pageable);
        }
        return null;
    }

    public BookStates getBookState(Long id) {
        return bookStateRepository.findById(id).orElseThrow();
    }

    public String addToToReadStatus(Long bookId) {
        // chwilowe rozwiąznie z id użytkonika
        Users user = userRepository.findByUserId(1L).orElseThrow();
        if (checkIfExist(bookId, user)) {
            return "exist";
        }

        Books book = booksRepository.findById(bookId).orElseThrow();
        Statuses status = statusesRepository.findStatusesByStatusName("to read").orElseThrow();

        BookStates bookState = new BookStates();

        bookState.setBook(book);
        bookState.setStatus(status);
        bookState.setUserID(user);
        bookStateRepository.save(bookState);
        return "correct";
    }

    public String addToInProgressStatus(Long bookId, AddBookToInProgressStatusDTO bookStatusData) {
        // chwilowe rozwiąznie z id użytkonika
        Users user = userRepository.findByUserId(1L).orElseThrow();

        if (checkIfExist(bookId, user)) {
            return "exist";
        }

        Books book = booksRepository.findById(bookId).orElseThrow();
        Statuses status = statusesRepository.findStatusesByStatusName("in progress").orElseThrow();

        BookStates bookState = new BookStates();
        saveWithInProgressStatus(bookState, book, status, user, bookStatusData);
        return "correct";
    }

    public void moveToInProgressStatus(Long bookId, AddBookToInProgressStatusDTO bookStatusData) {
        // chwilowe rozwiąznie z id użytkonika
        Users user = userRepository.findByUserId(1L).orElseThrow();

        Books book = booksRepository.findById(bookId).orElseThrow();
        Statuses status = statusesRepository.findStatusesByStatusName("in progress").orElseThrow();

        BookStates bookState = bookStateRepository.findBookStatesByBookAndUserID(book, user).orElseThrow();
        saveWithInProgressStatus(bookState, book, status, user, bookStatusData);
    }

    public String addBookToReadStatus(Long bookId, AddBookToReadStatusDTO bookStatusData) {
        // chwilowe rozwiąznie z id użytkonika
        Users user = userRepository.findByUserId(1L).orElseThrow();

        if (checkIfExist(bookId, user)) {
            return "exist";
        }
        Books book = booksRepository.findById(bookId).orElseThrow();
        Statuses status = statusesRepository.findStatusesByStatusName("read").orElseThrow();

        BookStates bookState = new BookStates();
        saveWithReadStatus(bookState, book, status, user, bookStatusData);
        return "correct";
    }

    public void moveBookToReadStatus(Long bookId, AddBookToReadStatusDTO bookStatusData) {
        // chwilowe rozwiąznie z id użytkonika
        Users user = userRepository.findByUserId(1L).orElseThrow();

        Books book = booksRepository.findById(bookId).orElseThrow();
        Statuses status = statusesRepository.findStatusesByStatusName("read").orElseThrow();

        BookStates bookState = bookStateRepository.findBookStatesByBookAndUserID(book, user).orElseThrow();
        saveWithReadStatus(bookState, book, status, user, bookStatusData);
    }

    public void deleteBookState(Long id) {
        bookStateRepository.deleteById(id);
    }

    private LocalDateTime fromStringToLocalDateTime(String stringDate) {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern, Locale.US);
        return LocalDateTime.parse(stringDate, dateTimeFormatter);
    }

    private void saveWithInProgressStatus(BookStates bookState, Books book, Statuses status, Users user, AddBookToInProgressStatusDTO bookStatusData ) {
        bookState.setBook(book);
        bookState.setStatus(status);
        bookState.setUserID(user);
        bookState.setStartDate(fromStringToLocalDateTime(bookStatusData.getStartDate()));
        bookState.setCurrentPage(bookStatusData.getCurrentPage());
        bookState.setEndDate(null);
        bookState.setRate(null);
        bookStateRepository.save(bookState);
    }

    private void saveWithReadStatus(BookStates bookState, Books book, Statuses status, Users user, AddBookToReadStatusDTO bookStatusData ) {
        bookState.setBook(book);
        bookState.setStatus(status);
        bookState.setCurrentPage(book.getPageNumber());
        bookState.setStartDate(fromStringToLocalDateTime(bookStatusData.getStartDate()));
        bookState.setEndDate(fromStringToLocalDateTime(bookStatusData.getEndDate()));
        bookState.setRate(bookStatusData.getRate());
        bookState.setUserID(user);
        bookStateRepository.save(bookState);
    }

    private Boolean checkIfExist(Long book_id, Users user) {
        return bookStateRepository.existsBookStatesByBook_BookIdAndUserID(book_id,user);
    }
}
