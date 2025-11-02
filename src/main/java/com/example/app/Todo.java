package com.example.app;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Todo {
    private int id;
    private String todo;
    private boolean completed;
    private int userId;
}
