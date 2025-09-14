package com.arpit.todo_list.service;

import com.arpit.todo_list.model.Users;
import com.arpit.todo_list.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @Autowired
    public UsersService(
            UserRepo userRepo,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JWTService jwtService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public String verify(Users user) {
        Authentication authentication
                = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(), user.getPassword()
                )
        );
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUsername());
        }
        return "Login failed";
    }

    public void registerUser(Users user) {
        if (userRepo.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("Username already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    public Users updateProfile(Long userId, Users user) {
        Users oldUserDetails = userRepo.findById(userId)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));
        if (!oldUserDetails.getUsername().equals(user.getUsername()) &&
                userRepo.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("Username already taken");
        }
        oldUserDetails.setUsername(user.getUsername());
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            oldUserDetails.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepo.save(oldUserDetails);
    }
}
