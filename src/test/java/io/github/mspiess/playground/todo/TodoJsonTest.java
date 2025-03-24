package io.github.mspiess.playground.todo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class TodoJsonTest {

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void serializes() throws JsonProcessingException {
        Todo todo = new Todo(1L, UUID.fromString("d6303cd1-eddd-43bf-96fc-f12f4b2de855"), "Some Title");

        String json = objectMapper.writeValueAsString(todo);

        assertThat(json).isEqualTo("{\"publicId\":\"d6303cd1-eddd-43bf-96fc-f12f4b2de855\",\"title\":\"Some Title\"}");
    }
}