package com.arpit.todo_list.controller;

import com.arpit.todo_list.model.Users;
import com.arpit.todo_list.repository.UserRepo;
import com.arpit.todo_list.service.JWTService;
import com.arpit.todo_list.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class UserController {

    private final UsersService usersService;
    private final UserRepo userRepo;

    @Autowired
    public UserController(UsersService usersService, UserRepo userRepo) {
        this.usersService = usersService;
        this.userRepo = userRepo;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody Users user) {
        if (user.getUsername().isBlank() ||
                user.getPassword().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        usersService.registerUser(user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody Users user) {
        String jwtToken = usersService.verify(user);
        if (jwtToken == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(jwtToken);
    }

    @PutMapping("/{userId}/profile")
    public ResponseEntity<Users> updateUserProfile(
            @PathVariable Long userId,
            @RequestBody Users user
    ) {
        try {
            Users updatedUser = usersService.updateProfile(userId, user);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{userId}/profile")
    public ResponseEntity<?> deleteUserProfile(@PathVariable Long userId) {
        if(userRepo.findById(userId).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        userRepo.deleteById(userId);
        return ResponseEntity.ok().build();
    }

}
