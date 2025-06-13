package com.gabrielferreira02.sbBank.core.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record Invoice(UUID id,
                      BigDecimal totalAmount,
                      LocalDateTime startDate,
                      LocalDateTime endDate,
                      LocalDateTime dueDate,
                      StatusType status) {
}
