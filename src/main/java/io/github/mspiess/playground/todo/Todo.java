package io.github.mspiess.playground.todo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

public record Todo(
        @JsonIgnore
        Long id,
        UUID publicId,
        String title
) {
}
