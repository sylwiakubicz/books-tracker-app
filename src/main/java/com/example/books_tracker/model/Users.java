package com.example.books_tracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.List;

@Entity
public class Users {
    @Id
    @GeneratedValue
    private Long userId;

    @NotNull
    private String username;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;

    @NotNull
    private Boolean active;

    @NotNull
    @ManyToOne
    @JoinColumn
    private UserRole role;

    @ManyToMany
    @JoinTable
    private List<BookState> bookList;

    @CreationTimestamp
    private Instant creationDate;
}
