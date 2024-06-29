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

    public static Specification<Users> findUsersSpecification(String username, String email, String role) {
        return (root, query, builder) -> {
            List<Predicate> predicateList = new ArrayList<>();

            if (username != null) {
                predicateList.add(builder.like(root.get("username"), "%" + username + "%"));
            }

            if (email != null) {
                predicateList.add(builder.like(root.get("email"), "%" + email + "%"));
            }

            if (role != null) {
                Join<Users, UserRoles> usersRolesJoin = root.join("role");
                predicateList.add(builder.equal(usersRolesJoin.get("role"), role));
            }

            return builder.and(predicateList.toArray(new Predicate[]{}));
        };
    }
}
