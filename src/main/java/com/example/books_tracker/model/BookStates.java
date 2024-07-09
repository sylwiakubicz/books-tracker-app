package com.example.books_tracker.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class BookStates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_state_id")
    private Long bookStateId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Users userID;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Statuses status;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Books book;

    private Integer currentPage;

    private Integer rate;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    public Users getUserID() {
        return userID;
    }

    public void setUserID(Users userID) {
        this.userID = userID;
    }

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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
