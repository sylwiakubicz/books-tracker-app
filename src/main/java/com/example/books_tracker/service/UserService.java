package com.example.books_tracker.service;

import com.example.books_tracker.DTO.CreateUserByAdminDTO;
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
        UserRoles role = roleRepository.findByRole("ROLE_USER").orElseThrow(() -> new NoSuchElementException("Role not found"));
        user.setRole(role);
        userRepository.save(user);
    }

    public void createUserByAdmin(CreateUserByAdminDTO createUserByAdminDTO) {
        Users user = new Users();
        user.setUsername(createUserByAdminDTO.getUsername());
        user.setPassword(passwordEncoder.encode(createUserByAdminDTO.getPassword()));
        user.setEmail(createUserByAdminDTO.getEmail());
        user.setActive(true);
        UserRoles role = roleRepository.findByRole(createUserByAdminDTO.getRole()).orElseThrow(() -> new NoSuchElementException("Role not found"));
        user.setRole(role);
        userRepository.save(user);
    }

    public void updateUser(Long id, CreateUserByAdminDTO userDetails) {
        Users user = userRepository.findByUserId(id).orElseThrow(() -> new NoSuchElementException("User not found"));
        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        UserRoles role = roleRepository.findByRole(userDetails.getRole()).orElseThrow(() -> new NoSuchElementException("Role not found"));
        user.setRole(role);
        user.setActive(true);
        userRepository.save(user);
    }

    public Page<Users> getAllUsers(String search, String role, Pageable pageable) {
        return userRepository.findAll(UserSpecification.findUsersSpecification(search, role), pageable);
    }

    public Users getUser(Long id) {
        return userRepository.findByUserId(id).orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    public Long getTotalUsers() {
        return userRepository.countTotalUsers();
    }

    public Long getAdminUsersCount() {
        return userRepository.countAdminUsers();
    }

    public Long getUserUsersCount() {
        return userRepository.countUserUsers();
    }
}
