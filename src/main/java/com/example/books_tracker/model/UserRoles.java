package com.example.books_tracker.model;

import jakarta.persistence.*;

@Entity
public class UserRoles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    private String name;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String role) {
        this.name = role;
    }
}
