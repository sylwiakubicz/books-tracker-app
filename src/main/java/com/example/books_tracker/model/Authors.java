package com.example.books_tracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public class Authors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorId;

    @NotNull
    private String name;

    @NotNull
    private String surname;
}
