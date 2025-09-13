package com.arpit.todo_list.controller;

import com.arpit.todo_list.model.Tasks;
import com.arpit.todo_list.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{userId}/tasks")
    public ResponseEntity<List<Tasks>> displayTasks(@PathVariable Long userId) {
        List<Tasks> tasks = taskService.getTasks(userId);
        if (!tasks.isEmpty()) {
            return new ResponseEntity<>(tasks, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }

    @PostMapping("/{userId}/tasks")
    public ResponseEntity<?> createTask(@RequestBody Tasks task, @PathVariable Long userId) {
        int status = taskService.addTaskByUserId(task, userId);
        if (status == 1) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{userId}/tasks")
    public ResponseEntity<?> deleteTask(@PathVariable Long userId) {
        int status = taskService.deleteTask(userId);
        if (status == 1) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{userId}/tasks")
    public ResponseEntity<?> updateTask(@RequestBody Tasks task, @PathVariable Long userId) {
        int status = taskService.updateTask(task, userId);
        if (status == 1) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
