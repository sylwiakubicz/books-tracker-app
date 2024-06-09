package com.example.books_tracker.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public class Genres {
    @Id
    @GeneratedValue
    private Long genresId;

    @NotNull
    private String name;
}
