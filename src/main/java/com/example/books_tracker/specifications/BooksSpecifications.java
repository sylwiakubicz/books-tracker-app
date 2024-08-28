package com.example.books_tracker.specifications;

import com.example.books_tracker.model.Authors;
import com.example.books_tracker.model.Books;
import com.example.books_tracker.model.Genres;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class BooksSpecifications {

    private BooksSpecifications(){}

    public static Specification<Books> findBooksSpecification(String search, String title, String author, String genre, Integer year) {
        return (root, query, builder) -> {
            List<Predicate> predicateList = new ArrayList<>();

            if (search != null && !search.trim().isEmpty()) {
                String[] searchTerms = search.split(" ");

                // Join authors
                Join<Books, Authors> booksAuthorsJoin = root.join("authors");

                // Create predicates for each search term
                List<Predicate> searchPredicates = new ArrayList<>();
                for (String term : searchTerms) {
                    term = "%" + term + "%"; // Add wildcards for LIKE

                    Predicate titlePredicate = builder.like(root.get("title"), term);
                    Predicate namePredicate = builder.like(booksAuthorsJoin.get("name"), term);
                    Predicate surnamePredicate = builder.like(booksAuthorsJoin.get("surname"), term);

                    searchPredicates.add(builder.or(titlePredicate, namePredicate, surnamePredicate));
                }

                // Combine all search predicates with AND
                predicateList.add(builder.and(searchPredicates.toArray(new Predicate[0])));
            }

            if (title != null) {
                predicateList.add(builder.like(root.get("title"), "%" + title + "%"));
            }


            if (author != null) {
                String[] nameParts = author.split(" ");

                Join<Books, Authors> booksAuthorsJoin = root.join("authors");
                List<Predicate> authorPredicates = new ArrayList<>();

                if (nameParts.length >= 1) {
                    for (String part : nameParts) {
                        Predicate namePredicate = builder.like(booksAuthorsJoin.get("name"), "%" + part + "%");
                        Predicate surnamePredicate = builder.like(booksAuthorsJoin.get("surname"), "%" + part + "%");
                        authorPredicates.add(builder.or(namePredicate, surnamePredicate));
                    }
                    predicateList.add(builder.and(authorPredicates.toArray(new Predicate[0])));
                }
            }

            if (genre != null) {
                Join<Books, Genres> booksGenresJoin = root.join("genres");
                predicateList.add(builder.equal(booksGenresJoin.get("name"), genre));
            }


            if (year != null) {
                predicateList.add(builder.equal(root.get("publicationYear"), year));
            }

            return builder.and(predicateList.toArray(new Predicate[]{}));
        };
    }
}
