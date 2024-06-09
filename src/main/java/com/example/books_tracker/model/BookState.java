package com.example.books_tracker.model;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.List;

@Entity
public class BookState {

    @Id
    @GeneratedValue
    private Long bookStateId;

    @ManyToOne
    @JoinColumn
    private Status status;

    @ManyToOne
    @JoinColumn
    private Book book;

    private Integer currentPage;

    private Integer rate;

    private Instant startDate;

    private Instant endDate;

}
