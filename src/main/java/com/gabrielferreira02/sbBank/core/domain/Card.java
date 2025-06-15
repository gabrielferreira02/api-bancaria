package com.gabrielferreira02.sbBank.core.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record Card(UUID id,
                   String number,
                   Account account,
                   BigDecimal limit,
                   LocalDateTime createdAt,
                   LocalDateTime updatedAt,
                   int paymentDay,
                   boolean active) {
}
