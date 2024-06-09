package com.example.books_tracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public class Author {
    @Id
    @GeneratedValue
    private Long authorId;

    @NotNull
    private String name;

    @NotNull
    private String surname;
}
