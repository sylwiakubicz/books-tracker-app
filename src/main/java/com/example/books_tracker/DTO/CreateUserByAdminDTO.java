package com.example.books_tracker.DTO;

import com.example.books_tracker.model.UserRoles;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CreateUserByAdminDTO {

    @NotBlank(message = "Username can not be null")
    private String username;

    @NotBlank(message = "Email can not be null")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password can not be null")
    private String password;

    private String role;

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}
