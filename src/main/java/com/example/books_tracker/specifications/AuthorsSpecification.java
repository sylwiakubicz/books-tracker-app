package com.example.books_tracker.specifications;
import com.example.books_tracker.model.Authors;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class AuthorsSpecification {
    public AuthorsSpecification() {
    }

    public static Specification<Authors> findAuthorsSpecification(String search) {
        return (root, query, builder) -> {
            List<Predicate> predicateList = new ArrayList<>();

            if (search != null && !search.isEmpty()) {
                String searchPattern = "%" + search + "%";
                Predicate namePredicate = builder.like(root.get("name"), searchPattern);
                Predicate surnamePredicate = builder.like(root.get("surname"), searchPattern);
                predicateList.add(builder.or(namePredicate, surnamePredicate));
            }

            return builder.and(predicateList.toArray(new Predicate[]{}));
        };
    }
}
