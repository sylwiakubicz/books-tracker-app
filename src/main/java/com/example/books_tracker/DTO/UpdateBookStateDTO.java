package com.example.books_tracker.DTO;

import com.example.books_tracker.model.Statuses;

import java.time.Instant;

public class UpdateBookStateDTO {
    private Statuses status;
    private Integer currentPage;
    private Integer rate;
    private Instant startDate;
    private Instant endDate;
}
