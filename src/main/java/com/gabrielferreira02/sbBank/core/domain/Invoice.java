package com.gabrielferreira02.sbBank.core.domain;

import com.gabrielferreira02.sbBank.core.enums.StatusType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record Invoice(UUID id,
                      Card card,
                      BigDecimal totalAmount,
                      LocalDateTime startDate,
                      LocalDateTime endDate,
                      LocalDateTime dueDate,
                      StatusType status) {
}
