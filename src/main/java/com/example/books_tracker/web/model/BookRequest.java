package com.example.books_tracker.web.model;

import com.example.books_tracker.data.entities.Authors;
import com.example.books_tracker.data.entities.Genres;

import java.util.Date;
import java.util.List;

public record BookRequest(String title,
                          String description,
                          Integer pageNumber,
                          byte[] covering,
                          Date publicationYear,
                          List<AuthorsRequest> authors,
                          List<GenresRequest> genres
) {}
