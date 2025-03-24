package io.github.mspiess.playground.todo;

import java.util.UUID;

public record TodoDao(
        Long id,
        UUID publicId,
        String title
) {
}
