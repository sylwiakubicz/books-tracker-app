package com.example.books_tracker.specifications;

import com.example.books_tracker.model.BookStates;
import com.example.books_tracker.model.Statuses;
import com.example.books_tracker.model.Users;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class BookStatesSpecification {
    public BookStatesSpecification() {
    }

    public static Specification<BookStates> findBookStatesSpecification(Users user, Statuses status, Integer rate) {
        return (root, query, builder) -> {
            List<Predicate> predicateList = new ArrayList<>();

            predicateList.add(builder.equal(root.get("userID"), user));

            if (status != null ) {
                predicateList.add(builder.equal(root.get("status"), status));
            }

            if (rate != null) {
                predicateList.add(builder.greaterThanOrEqualTo(root.get("rate"), rate));
            }

            // Sortowanie według określonego porządku statusów
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
