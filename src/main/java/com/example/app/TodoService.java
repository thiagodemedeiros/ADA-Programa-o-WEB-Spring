package com.example.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;

public class TodoService {
    private static final String BASE_URL = "https://dummyjson.com/todos";
    private final ObjectMapper mapper = new ObjectMapper();

    public List<Todo> list(int limit, int skip) throws IOException, InterruptedException {
        String json = HttpClientUtil.get(BASE_URL + "?limit=" + limit + "&skip=" + skip);
        TodoListResponse response = mapper.readValue(json, TodoListResponse.class);
        return response.getTodos();
    }

    public Todo add(String text, int userId) throws IOException, InterruptedException {
        String body = String.format("{\"todo\":\"%s\",\"userId\":%d}", text, userId);
        String json = HttpClientUtil.post(BASE_URL + "/add", body);
        return mapper.readValue(json, Todo.class);
    }

    public Todo toggle(int id, boolean completed) throws IOException, InterruptedException {
        String body = String.format("{\"completed\":%b}", completed);
        String json = HttpClientUtil.put(BASE_URL + "/" + id, body);
        return mapper.readValue(json, Todo.class);
    }

    public boolean delete(int id) throws IOException, InterruptedException {
        try {
            HttpClientUtil.delete(BASE_URL + "/" + id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
