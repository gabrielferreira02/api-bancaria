package com.gabrielferreira02.sbBank.core.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record Account(UUID id,
                      User user,
                      String accountNumber,
                      BigDecimal balance,
                      BigDecimal dailyTransferLimit,
                      LocalDateTime createdAt) {
}
