package com.example.books_tracker.specifications;

import com.example.books_tracker.model.*;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserSpecification {
    public UserSpecification() {
    }

    public static Specification<Users> findUsersSpecification(String search, String role) {
        return (root, query, builder) -> {
            List<Predicate> predicateList = new ArrayList<>();

            if (search != null && !search.isEmpty()) {
                Predicate usernamePredicate = builder.like(root.get("username"), "%" + search + "%");
                Predicate emailPredicate = builder.like(root.get("email"), "%" + search + "%");
                predicateList.add(builder.or(usernamePredicate, emailPredicate));
            }

            if (role != null) {
                Join<Users, UserRoles> usersRolesJoin = root.join("role");
                predicateList.add(builder.equal(usersRolesJoin.get("role"), role));
            }

            return builder.and(predicateList.toArray(new Predicate[]{}));
        };
    }
}
