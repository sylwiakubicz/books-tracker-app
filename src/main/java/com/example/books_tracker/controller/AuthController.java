package com.example.books_tracker.controller;

import com.example.books_tracker.DTO.SignInDTO;
import com.example.books_tracker.DTO.SignUpDTO;
import com.example.books_tracker.model.Users;
import com.example.books_tracker.repository.UserRepository;
import com.example.books_tracker.service.CustomUserDetailsService;
import com.example.books_tracker.service.UserService;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    public static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

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
    public ResponseEntity<String> login(@RequestBody SignInDTO signInDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signInDTO.getUsername(), signInDTO.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            return ResponseEntity.ok("User logged in successfully");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong username or password");
        }
    }

    @PutMapping("/update/user/{id}")
    public ResponseEntity<?> updateUser(@RequestBody Users user, @PathVariable Long id) {
        userService.updateUser(id, user);
        return ResponseEntity.status(HttpStatus.OK).body("User updated");
    }

    @GetMapping
    public List<Users> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Users getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("User deleted");
    }
}
