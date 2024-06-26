package com.example.books_tracker.controller;

import com.example.books_tracker.model.Users;
import com.example.books_tracker.repository.UserRepository;
import com.example.books_tracker.service.CustomUserDetailsService;
import com.example.books_tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final CustomUserDetailsService userDetailsService;

    private final UserRepository userRepository;

    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, CustomUserDetailsService userDetailsService, UserRepository userRepository, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody SignUpDTO signUpDTO) {

        if (userRepository.existsByEmail(signUpDTO.getEmail())) {
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByUsername(signUpDTO.getUsername())) {
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        Users user = userService.createUser(signUpDTO);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    @PostMapping("/login")
    public String login(@RequestBody SignUpDTO authenticationRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "User logged in successfully";
    }
}
