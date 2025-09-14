package com.arpit.todo_list;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodoListApplication {

    public static void main(String[] args) {
        System.out.println("TodoListApplication start");
        SpringApplication.run(TodoListApplication.class, args);
    }

}
