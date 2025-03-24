package io.github.mspiess.playground.todo;

import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Window;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class TodoController {

    private final JdbcClient client;

    public TodoController(JdbcClient client) {
        this.client = client;
    }

    @QueryMapping
    public Window<Todo> getTodos() {
        List<Todo> todos = client.sql("SELECT * FROM todo").query(Todo.class).list();
        return Window.from(todos, ScrollPosition::offset);
    }
}
