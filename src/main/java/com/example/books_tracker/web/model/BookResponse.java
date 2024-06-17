package com.example.books_tracker.web.model;

import com.example.books_tracker.data.entities.Books;
import com.example.books_tracker.data.entities.Authors;
import com.example.books_tracker.data.entities.Genres;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public record BookResponse(
        Long bookId,
        String title,
        String description,
        Integer pageNumber,
        byte[] covering,
        Date publicationYear,
        List<Long> authorIds,
        List<Long> genreIds
) {
    public BookResponse(Books book) {
        this(
                book.getBookId(),
                book.getTitle(),
                book.getDescription(),
                book.getPageNumber(),
                book.getCovering(),
                book.getPublicationYear(),
                book.getAuthors().stream().map(Authors::getAuthorId).collect(Collectors.toList()),
                book.getGenres().stream().map(Genres::getGenresId).collect(Collectors.toList())
        );
    }
}
