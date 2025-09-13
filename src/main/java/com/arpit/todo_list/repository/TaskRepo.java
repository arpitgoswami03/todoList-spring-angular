package com.arpit.todo_list.repository;

import com.arpit.todo_list.model.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepo extends JpaRepository<Tasks, Long> {
    List<Tasks> findAllByUserId(Long id);
}
