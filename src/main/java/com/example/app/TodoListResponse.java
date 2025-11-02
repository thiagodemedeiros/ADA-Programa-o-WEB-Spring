package com.example.app;

import lombok.Data;
import java.util.List;

@Data
public class TodoListResponse {
    private List<Todo> todos;
    private int total;
    private int skip;
    private int limit;
}
