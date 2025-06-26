package com.gabrielferreira02.sbBank.core.dto;

public record CreateCustomerResponse(
        String name,
        String email,
        String cep,
        String phone,
        String accountNumber,
        String cardNumber
) {
}
