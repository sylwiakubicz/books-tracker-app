package com.example.books_tracker.DTO;

import com.example.books_tracker.model.Books;

import java.time.Instant;

public class CreateBookStateDTO {
    private Books book;
    private Instant startDate;

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Books getBook() {
        return book;
    }

    public void setBook(Books book) {
        this.book = book;
    }
}
