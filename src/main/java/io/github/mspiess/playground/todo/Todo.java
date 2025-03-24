package io.github.mspiess.playground.todo;

import java.util.UUID;

public record Todo(
        Long id,
        UUID publicId,
        String title
) {
}
