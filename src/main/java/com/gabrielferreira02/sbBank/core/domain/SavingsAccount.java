package com.gabrielferreira02.sbBank.core.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record SavingsAccount(UUID id,
                             User user,
                             BigDecimal balance,
                             LocalDate openingDate,
                             boolean active
                             ) {
}
