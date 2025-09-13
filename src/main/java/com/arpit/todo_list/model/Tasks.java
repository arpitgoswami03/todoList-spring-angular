package com.arpit.todo_list.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Tasks {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String task;
    private boolean done;
    private Date dueDate;
    private Date dateCreated;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
}
