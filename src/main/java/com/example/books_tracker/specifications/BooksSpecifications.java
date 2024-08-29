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

    public static Specification<Books> findBooksSpecification(String search, String genre, Integer year) {
        return (root, query, builder) -> {
            List<Predicate> predicateList = new ArrayList<>();

            if (search != null && !search.trim().isEmpty()) {
                String[] searchTerms = search.split(" ");

                Join<Books, Authors> booksAuthorsJoin = root.join("authors");

                List<Predicate> searchPredicates = new ArrayList<>();
                for (String term : searchTerms) {
                    term = "%" + term + "%";

                    Predicate titlePredicate = builder.like(root.get("title"), term);
                    Predicate namePredicate = builder.like(booksAuthorsJoin.get("name"), term);
                    Predicate surnamePredicate = builder.like(booksAuthorsJoin.get("surname"), term);

                    searchPredicates.add(builder.or(titlePredicate, namePredicate, surnamePredicate));
                }

                predicateList.add(builder.and(searchPredicates.toArray(new Predicate[0])));
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
