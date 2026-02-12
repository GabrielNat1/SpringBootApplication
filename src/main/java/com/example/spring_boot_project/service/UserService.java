package com.example.spring_boot_project.service;

import com.example.spring_boot_project.exceptions.EmailAlreadyExistsException;
import com.example.spring_boot_project.exceptions.UsernameAlreadyExistsException;
import com.example.spring_boot_project.model.User;
import com.example.spring_boot_project.repository.UserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Primary
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().replace("ROLE_", ""))
                .build();
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User registerUser(String username,
                             String rawPassword,
                             String email,
                             String telephone,
                             String ipAddress,
                             String userAgent) {
        if (userRepository.existsByUsername(username)) {
            throw new UsernameAlreadyExistsException();
        }

        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        try{
            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(rawPassword));
            user.setEmail(email);
            user.setTelephone(telephone);
            user.setRole("ROLE_USER");

            return userRepository.save(user);
        } catch (DataIntegrityViolationException ex){
            throw new EmailAlreadyExistsException("Email already exists");
        }
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}

