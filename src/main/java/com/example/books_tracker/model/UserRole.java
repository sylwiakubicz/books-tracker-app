package com.example.books_tracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class UserRole {

    @Id
    @GeneratedValue
    private Long roleId;

    @NotNull
    private String role;
}
