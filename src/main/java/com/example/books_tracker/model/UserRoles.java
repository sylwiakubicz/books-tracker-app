package com.example.books_tracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class UserRoles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @NotNull
    private String role;
}
