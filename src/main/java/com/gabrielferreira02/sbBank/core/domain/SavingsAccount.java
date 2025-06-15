package com.gabrielferreira02.sbBank.core.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record SavingsAccount(UUID id,
                             User user,
                             BigDecimal balance,
                             LocalDateTime openingDate,
                             LocalDateTime created_at,
                             LocalDateTime updated_at) {}
