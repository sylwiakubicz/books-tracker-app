package com.example.books_tracker.data.entities;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class BookStates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookStateId;

    @ManyToOne
    @JoinColumn
    private Statuses status;

    @ManyToOne
    @JoinColumn
    private Books book;

    private Integer currentPage;

    private Integer rate;

    private Instant startDate;

    private Instant endDate;


    public Long getBookStateId() {
        return bookStateId;
    }

    public void setBookStateId(Long bookStateId) {
        this.bookStateId = bookStateId;
    }

    public Statuses getStatus() {
        return status;
    }

    public void setStatus(Statuses status) {
        this.status = status;
    }

    public Books getBook() {
        return book;
    }

    public void setBook(Books book) {
        this.book = book;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }
}
