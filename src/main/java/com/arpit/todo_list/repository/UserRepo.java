package com.arpit.todo_list.repository;

import com.arpit.todo_list.model.UserDetail;
import com.arpit.todo_list.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<Users, Long> {
    Users findByUsername(String username);
}
