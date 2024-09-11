package com.example.books_tracker.specifications;

import com.example.books_tracker.model.Genres;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class GenresSpecification {
    public GenresSpecification() {
    }

    public static Specification<Genres> findGenresSpecification(String search) {
        return (root, query, builder) -> {
            List<Predicate> predicateList = new ArrayList<>();

            if (search != null && !search.isEmpty()) {
                String searchPattern = "%" + search + "%";
                Predicate namePredicate = builder.like(root.get("name"), searchPattern);
                predicateList.add(namePredicate);
            }

            return builder.and(predicateList.toArray(new Predicate[0]));
        };
    }
}
