package com.example.books_tracker.data.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public class Genres {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long genresId;

    @NotNull
    private String name;

    public Genres() {
    }

    public Genres(String name) {
    }

    public Long getGenresId() {
        return genresId;
    }

    public void setGenresId(Long genresId) {
        this.genresId = genresId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
