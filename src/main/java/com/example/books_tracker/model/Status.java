package com.example.books_tracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Status {

    @Id
    @GeneratedValue
    private Long id;

    private String statusName;
}
