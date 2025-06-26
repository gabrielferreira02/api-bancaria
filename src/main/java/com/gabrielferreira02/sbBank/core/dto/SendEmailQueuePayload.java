package com.gabrielferreira02.sbBank.core.dto;

import java.util.UUID;

public record SendEmailQueuePayload(
        UUID userId,
        String name,
        String email
) {
}
