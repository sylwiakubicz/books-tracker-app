package com.example.books_tracker.specifications;

import com.example.books_tracker.data.entities.Authors;
import com.example.books_tracker.data.entities.Books;
import com.example.books_tracker.data.entities.Genres;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class BooksSpecifications {

    private BooksSpecifications(){}

    public static Specification<Books> findBooksSpecification(String title, String authorName, String authorSurname, String genre) {
        return (root, query, builder) -> {
            List<Predicate> predicateList = new ArrayList<>();

            if (title != null) {
                predicateList.add(builder.like(root.get("title"), "%" + title + "%"));
            }
            if (authorName != null) {
                Join<Books, Authors> booksAuthorsJoin = root.join("authors");
                predicateList.add(builder.equal(booksAuthorsJoin.get("name"), authorName));
            }

            if (authorSurname != null) {
                Join<Books, Authors> booksAuthorsJoin = root.join("authors");
                predicateList.add(builder.equal(booksAuthorsJoin.get("surname"), authorSurname));
            }

            if (genre != null) {
                Join<Books, Genres> booksGenresJoin = root.join("genres");
                predicateList.add(builder.equal(booksGenresJoin.get("name"), genre));
            }

            return builder.and(predicateList.toArray(new Predicate[]{}));
        };
    }
}
