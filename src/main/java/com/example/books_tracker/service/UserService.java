package com.example.books_tracker.service;

import com.example.books_tracker.controller.AuthenticationRequest;
import com.example.books_tracker.model.UserRoles;
import com.example.books_tracker.model.Users;
import com.example.books_tracker.repository.RoleRepository;
import com.example.books_tracker.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Users createUser(AuthenticationRequest authenticationRequest) {
        Users user = new Users();
        user.setUsername(authenticationRequest.getUsername());
        user.setPassword(passwordEncoder.encode(authenticationRequest.getPassword()));
        user.setEmail(authenticationRequest.getEmail());
        user.setActive(true);
        UserRoles role = roleRepository.findByRole("user").orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(role);

        return userRepository.save(user);
    }
}
