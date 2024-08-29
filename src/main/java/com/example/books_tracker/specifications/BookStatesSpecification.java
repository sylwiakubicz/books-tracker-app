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

    public static Specification<BookStates> findBookStatesSpecification(Users user, String status, Integer rate, String genre) {
        return (root, query, builder) -> {
            List<Predicate> predicateList = new ArrayList<>();

            predicateList.add(builder.equal(root.get("userID"), user));

            if (status != null ) {
                Join<BookStates, Statuses> bookStatesStatusesJoin = root.join("status");
                predicateList.add(builder.equal(bookStatesStatusesJoin.get("statusName"), status));
            }

            if (rate != null) {
                predicateList.add(builder.greaterThanOrEqualTo(root.get("rate"), rate));
            }

            if (genre != null) {
                Join<BookStates, Books> bookStatesBooksJoin = root.join("book");
                Join<Books, Genres> booksGenresJoin = bookStatesBooksJoin.join("genres");
                predicateList.add(builder.equal(booksGenresJoin.get("name"), genre));
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
