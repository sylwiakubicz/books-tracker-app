package com.example.books_tracker.service;

import com.example.books_tracker.DTO.SignUpDTO;
import com.example.books_tracker.model.UserRoles;
import com.example.books_tracker.model.Users;
import com.example.books_tracker.repository.RoleRepository;
import com.example.books_tracker.repository.UserRepository;
import com.example.books_tracker.specifications.UserSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

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

    public void createUser(SignUpDTO signUpDTO) {
        Users user = new Users();
        user.setUsername(signUpDTO.getUsername());
        user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        user.setEmail(signUpDTO.getEmail());
        user.setActive(true);
        UserRoles role = roleRepository.findByRole("user").orElseThrow(() -> new NoSuchElementException("Role not found"));
        user.setRole(role);
        userRepository.save(user);
    }

    public void updateUser(Long id, Users userDetails) {
        Users user = userRepository.findByUserId(id).orElseThrow();
        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        user.setRole(userDetails.getRole());
        user.setActive(userDetails.getActive());
        userRepository.save(user);
    }

    public Page<Users> getAllUsers(String username, String email, String role, Pageable pageable) {
        return userRepository.findAll(UserSpecification.findUsersSpecification(username, email, role), pageable);
    }

    public Users getUser(Long id) {
        return userRepository.findByUserId(id).orElseThrow();
    }
}
