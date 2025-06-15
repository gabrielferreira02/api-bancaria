package com.gabrielferreira02.sbBank.core.domain;

import com.gabrielferreira02.sbBank.core.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record Transaction(UUID id,
                          BigDecimal amount,
                          double transferFee,
                          BigDecimal feeAmount,
                          TransactionType type,
                          LocalDateTime createdAt,
                          Account sender,
                          Account receiver) {
}
