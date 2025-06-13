package com.gabrielferreira02.sbBank.core.domain;

import com.gabrielferreira02.sbBank.core.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record Transaction(UUID id,
                          BigDecimal amount,
                          TransactionType type,
                          LocalDateTime createdAt,
                          User sender,
                          User receiver) {
}
