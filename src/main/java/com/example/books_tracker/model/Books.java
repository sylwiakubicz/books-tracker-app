package com.example.books_tracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;

@Entity
public class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private Integer pageNumber;

    private byte[] covering;

    @NotNull
    private Date publicationYear;

    @NotNull
    @ManyToMany
    @JoinTable
    private List<Authors> authors;

    @NotNull
    @ManyToMany
    @JoinTable
    private List<Genres> genres;


}
