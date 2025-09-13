package com.arpit.todo_list.controller;

import com.arpit.todo_list.model.Users;
import com.arpit.todo_list.repository.UserRepo;
import com.arpit.todo_list.service.UsersService;
import org.apache.tomcat.jni.CertificateVerifier;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private final UserRepo userRepo;
    private final UsersService userService;

    @Autowired
    public UserController(UserRepo userRepo, UsersService userService) {
        this.userRepo = userRepo;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam Users user) {
        return ResponseEntity.ok(userService.verify(user));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestParam Users user) {
        if(user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        if(userRepo.findByEUsername(user.getUsername()).isPresent()){
            throw new RuntimeException("Username already exists");
        }
        userService.registerUser(user);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{userId}/profile")
    public ResponseEntity<?> updateUserDetails(@PathVariable long userId, @RequestBody Users user) {
        Users oldUserDetails =  userRepo.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        oldUserDetails.setUsername(user.getUsername());
        oldUserDetails.setPassword(user.getPassword());
        userRepo.save(oldUserDetails);
        return ResponseEntity.ok().build();
    }

}
