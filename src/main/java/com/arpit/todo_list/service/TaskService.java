package com.arpit.todo_list.service;

import com.arpit.todo_list.model.Tasks;
import com.arpit.todo_list.repository.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepo taskRepo;

    @Autowired
    public TaskService(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }

    public List<Tasks> getTasks(){
        return taskRepo.findAll();
    }

    public int addTask(Tasks task) {
        taskRepo.save(task);
        if(taskRepo.findById(task.getId()).isPresent()){
            return 1;
        }
        return 0;
    }

    public int deleteTask(Long id) {
        taskRepo.deleteById(id);
        if(taskRepo.findById(id).isPresent()){
            return 1;
        }
        return 0;
    }

    public int updateTask(Tasks task) {
        taskRepo.save(task);
        if(taskRepo.findById(task.getId()).isPresent()){
            return 1;
        }
        return 0;
    }
}
