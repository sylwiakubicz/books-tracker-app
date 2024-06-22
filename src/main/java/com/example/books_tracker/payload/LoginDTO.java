package com.example.books_tracker.payload;

import lombok.Data;

public class LoginDTO {
    @Data
    public class LoginDto {
        private String usernameOrEmail;
        private String password;
    }
}
