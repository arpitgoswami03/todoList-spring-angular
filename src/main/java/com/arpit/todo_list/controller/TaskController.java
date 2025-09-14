package com.arpit.todo_list.controller;

import com.arpit.todo_list.model.Tasks;
import com.arpit.todo_list.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/{userId}/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<Tasks>> displayTasks(
            @PathVariable Long userId) {
        List<Tasks> tasks = taskService.getTasksByUserId(userId);
        if (!tasks.isEmpty()) {
            return ResponseEntity.ok(tasks);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Tasks> displayTask(
            @PathVariable Long userId,
            @PathVariable Long taskId) {
        Tasks tasks = taskService.getTask(userId, taskId);
        if (tasks!=null) {
            return ResponseEntity.ok(tasks);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<?> createTask(
            @RequestBody Tasks task,
            @PathVariable Long userId) {
        int status = taskService.addTaskByUserId(task, userId);
        if (status == 1) {
            return ResponseEntity.accepted().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(
            @PathVariable Long userId,
            @PathVariable Long taskId) {
        int status = taskService.deleteTaskByUserId(userId, taskId);
        if (status == 1) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<?> updateTask(
            @RequestBody Tasks task,
            @PathVariable Long userId,
            @PathVariable Long taskId) {
        int status = taskService.updateTask(task, userId, taskId);
        if (status == 1) {
            return ResponseEntity.accepted().build();
        }
        return ResponseEntity.badRequest().build();
    }

}
