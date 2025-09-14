package com.arpit.todo_list.service;

import com.arpit.todo_list.model.Tasks;
import com.arpit.todo_list.model.Users;
import com.arpit.todo_list.repository.TaskRepo;
import com.arpit.todo_list.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class TaskService {

    private final TaskRepo taskRepo;
    private final UserRepo userRepo;

    @Autowired
    public TaskService(TaskRepo taskRepo, UserRepo userRepo) {
        this.userRepo = userRepo;
        this.taskRepo = taskRepo;
    }

    public List<Tasks> getTasksByUserId(Long userId) {
        return taskRepo.findAll().stream()
                .filter(tasks -> {
                    return Objects.equals(tasks.getUser().getId(), userId);
                }).toList();
    }

    public Tasks getTask(Long userId, Long taskId) {
        return taskRepo.findTaskById(userId, taskId);
    }

    public int deleteTaskByUserId(Long userId, Long taskId) {
        taskRepo.deleteByIdAndUserId(taskId, userId);
        if (taskRepo.findById(taskId).isEmpty()) {
            return 1;
        }
        return 0;
    }

    public int updateTask(Tasks task, Long userId,  Long taskId) {
        if (taskRepo.findById(taskId).isEmpty()) {
            return 0;
        }
        Tasks oldTask = taskRepo.findById(task.getId()).orElseThrow(() ->
                new RuntimeException("Task not found"));
        oldTask.setDone(task.isDone());
        oldTask.setDueDate(task.getDueDate());
        oldTask.setTask(task.getTask());
        taskRepo.save(oldTask);
        return 1;
    }

    public int addTaskByUserId(Tasks task, Long userId) {
        if (userRepo.findById(userId).isEmpty()) {
            return 0;
        }
        Users user = userRepo.findById(userId).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));
        if (task.getDueDate() == null) {
            task.setDueDate(LocalDateTime.now().plusDays(1));
        }
        task.setUser(user);
        taskRepo.save(task);
        return 1;
    }
}
