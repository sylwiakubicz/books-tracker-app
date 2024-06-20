package com.example.books_tracker.service;

import com.example.books_tracker.model.Users;
import com.example.books_tracker.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Users user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow(() ->
                new UsernameNotFoundException("User not found with username or email: "+ usernameOrEmail));

        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getName());

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                Collections.singleton(authority));
    }
}
