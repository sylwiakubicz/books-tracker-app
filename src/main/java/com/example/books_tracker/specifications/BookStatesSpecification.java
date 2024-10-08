package com.example.books_tracker.specifications;

import com.example.books_tracker.model.*;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class BookStatesSpecification {
    public BookStatesSpecification() {
    }

    public static Specification<BookStates> findBookStatesSpecification(Users user, String status, String genre, String search) {
        return (root, query, builder) -> {
            List<Predicate> predicateList = new ArrayList<>();

            predicateList.add(builder.equal(root.get("userID"), user));

            if (status != null ) {
                Join<BookStates, Statuses> bookStatesStatusesJoin = root.join("status");
                predicateList.add(builder.equal(bookStatesStatusesJoin.get("statusName"), status));
            }

            if (genre != null) {
                Join<BookStates, Books> bookStatesBooksJoin = root.join("book");
                Join<Books, Genres> booksGenresJoin = bookStatesBooksJoin.join("genres");
                predicateList.add(builder.equal(booksGenresJoin.get("name"), genre));
            }

            if (search != null) {
                String[] searchTerms = search.split(" ");

                Join<BookStates, Books> bookStatesBooksJoin = root.join("book");
                Join<Books,Authors> booksAuthorsJoin = bookStatesBooksJoin.join("authors");

                List<Predicate> searchPredicates = new ArrayList<>();
                for (String term : searchTerms) {
                    term = "%" + term + "%";
                    Predicate titlePredicate = builder.like(bookStatesBooksJoin.get("title"), term);
                    Predicate namePredicate = builder.like(booksAuthorsJoin.get("name"), term);
                    Predicate surnamePredicate = builder.like(booksAuthorsJoin.get("surname"), term);

                    searchPredicates.add(builder.or(titlePredicate, namePredicate, surnamePredicate));
                }
                predicateList.add(builder.and(searchPredicates.toArray(new Predicate[0])));
            }

            query.orderBy(
                    builder.asc(
                            builder.selectCase()
                                    .when(builder.equal(root.get("status").get("statusName"), "Currently reading"), 1)
                                    .when(builder.equal(root.get("status").get("statusName"), "Want to read"), 2)
                                    .when(builder.equal(root.get("status").get("statusName"), "Read"), 3)
                                    .otherwise(4)
                    )
            );

            return builder.and(predicateList.toArray(new Predicate[]{}));
        };
    }
}
