package com.gabrielferreira02.sbBank.core.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public record User(UUID id,
                   String name,
                   String cpf,
                   String email,
                   String password,
                   boolean verified,
                   String cep,
                   String cepComplement,
                   String phone,
                   LocalDateTime createdAt
                   ) {
}
