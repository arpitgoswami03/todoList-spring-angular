package com.arpit.todo_list.service;

import com.arpit.todo_list.model.Tasks;
import com.arpit.todo_list.model.Users;
import com.arpit.todo_list.repository.TaskRepo;
import com.arpit.todo_list.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepo taskRepo;
    private final UserRepo userRepo;

    @Autowired
    public TaskService(TaskRepo taskRepo, UserRepo userRepo) {
        this.userRepo = userRepo;
        this.taskRepo = taskRepo;
    }

    public List<Tasks> getTasks(Long userId) {
        return taskRepo.findAllByUserId(userId);
    }

    public int deleteTask(Long id) {
        taskRepo.deleteById(id);
        if(taskRepo.findById(id).isPresent()){
            return 1;
        }
        return 0;
    }

    public int updateTask(Tasks task, Long userId) {
        if(userRepo.findById(userId).isEmpty()){
            return 0;
        }
        Users user = userRepo.findById(userId).orElseThrow(() ->  new UsernameNotFoundException("User not found"));
        Tasks oldTask = taskRepo.findById(task.getId()).orElseThrow(() ->new RuntimeException("Task not found"));
        oldTask.setDone(task.isDone());
        oldTask.setDueDate(task.getDueDate());
        oldTask.setTask(task.getTask());
        taskRepo.save(oldTask);
        return 1;
    }

    public int addTaskByUserId(Tasks task, Long userId) {
        if(userRepo.findById(userId).isEmpty()){
            return 0;
        }
        Users user = userRepo.findById(userId).orElseThrow(() ->  new UsernameNotFoundException("User not found"));
        task.setUser(user);
        return 1;
    }
}
