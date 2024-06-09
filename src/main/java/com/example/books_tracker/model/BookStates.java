package com.example.books_tracker.model;

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

}
