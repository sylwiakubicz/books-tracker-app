package com.example.books_tracker.DTO;

import com.example.books_tracker.model.Authors;
import com.example.books_tracker.model.Genres;

import java.util.List;

public class BookData {

    private String title;
    private String description;
    private Integer pageNumber;
    private String covering;
    private Integer publicationYear;
    private List<Authors> authors;
    private List<Genres> genres;
    private String ISBN;


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public String getCovering() {
        return covering;
    }

    public void setCovering(String covering) {
        this.covering = covering;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public List<Authors> getAuthor() {
        return authors;
    }

    public List<Genres> getGenres() {
        return genres;
    }

    public String getISBN() {
        return ISBN;
    }

}
