package com.arpit.todo_list.repository;

import com.arpit.todo_list.model.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepo extends JpaRepository<Tasks, Long> {
}
