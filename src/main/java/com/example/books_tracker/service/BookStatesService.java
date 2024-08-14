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
import java.util.NoSuchElementException;

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

    public Page<BookStates> getAll(Long user_id, String status, Integer rate, Pageable pageable) {
        Users user = userRepository.findByUserId(user_id).orElseThrow(() -> new NoSuchElementException("User not found"));
        if (status != null) {
            Statuses statusObject = statusesRepository.findStatusesByStatusName(status).orElseThrow(() -> new NoSuchElementException("Status not found"));
            if (rate != null) {
                return bookStateRepository.findAll(BookStatesSpecification.findBookStatesSpecification(user, statusObject,rate), pageable);
            }
            return bookStateRepository.findAll( BookStatesSpecification.findBookStatesSpecification(user,statusObject, null), pageable);
        } else if (rate != null) {
            return bookStateRepository.findAll( BookStatesSpecification.findBookStatesSpecification(user,null, rate), pageable);
        } else {
            return bookStateRepository.findAll( BookStatesSpecification.findBookStatesSpecification(user,null, null), pageable);
        }
    }

    public BookStates getBookState(Long id) {
        return bookStateRepository.findById(id).orElseThrow(() -> new NoSuchElementException("BookState not found"));
    }

    public String addToToReadStatus(Long bookId, String username) {
        Users user = userRepository.findByUsername(username).orElseThrow(() -> new NoSuchElementException("User not found"));
        if (checkIfExist(bookId, user)) {
            return "exist";
        }

        Books book = booksRepository.findById(bookId).orElseThrow(() -> new NoSuchElementException("Book not found"));
        Statuses status = statusesRepository.findStatusesByStatusName("to read").orElseThrow(() -> new NoSuchElementException("Status not found"));

        BookStates bookState = new BookStates();

        bookState.setBook(book);
        bookState.setStatus(status);
        bookState.setUserID(user);
        bookStateRepository.save(bookState);
        return "correct";
    }

    public String addToInProgressStatus(Long bookId, AddBookToInProgressStatusDTO bookStatusData, String username) {
        Users user = userRepository.findByUsername(username).orElseThrow(() -> new NoSuchElementException("User not found"));
        System.out.println("user " + user);

        if (checkIfExist(bookId, user)) {
            return "exist";
        }

        Books book = booksRepository.findById(bookId).orElseThrow(() -> new NoSuchElementException("Book not found"));
        Statuses status = statusesRepository.findStatusesByStatusName("in progress").orElseThrow(() -> new NoSuchElementException("Status not found"));

        BookStates bookState = new BookStates();
        saveWithInProgressStatus(bookState, book, status, user, bookStatusData);
        return "correct";
    }

    public void moveToInProgressStatus(Long bookId, AddBookToInProgressStatusDTO bookStatusData, String username) {
        Users user = userRepository.findByUsername(username).orElseThrow(() -> new NoSuchElementException("User not found"));
        Books book = booksRepository.findById(bookId).orElseThrow(() -> new NoSuchElementException("Book not found"));

        Statuses status = statusesRepository.findStatusesByStatusName("in progress").orElseThrow(() -> new NoSuchElementException("Status not found"));

        BookStates bookState = bookStateRepository.findBookStatesByBookAndUserID(book, user).orElseThrow(() -> new NoSuchElementException("BookStatus not found"));
        saveWithInProgressStatus(bookState, book, status, user, bookStatusData);
    }

    public String addBookToReadStatus(Long bookId, AddBookToReadStatusDTO bookStatusData, String username) {
        Users user = userRepository.findByUsername(username).orElseThrow(() -> new NoSuchElementException("User not found"));

        if (checkIfExist(bookId, user)) {
            return "exist";
        }
        Books book = booksRepository.findById(bookId).orElseThrow(() -> new NoSuchElementException("Book not found"));
        Statuses status = statusesRepository.findStatusesByStatusName("read").orElseThrow(() -> new NoSuchElementException("Status not found"));

        BookStates bookState = new BookStates();
        saveWithReadStatus(bookState, book, status, user, bookStatusData);
        return "correct";
    }

    public void moveBookToReadStatus(Long bookId, AddBookToReadStatusDTO bookStatusData, String username) {
        Users user = userRepository.findByUsername(username).orElseThrow(() -> new NoSuchElementException("User not found"));

        Books book = booksRepository.findById(bookId).orElseThrow(() -> new NoSuchElementException("Book not found"));
        Statuses status = statusesRepository.findStatusesByStatusName("read").orElseThrow(() -> new NoSuchElementException("Status not found"));

        BookStates bookState = bookStateRepository.findBookStatesByBookAndUserID(book, user).orElseThrow(() -> new NoSuchElementException("BookState not found"));
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
