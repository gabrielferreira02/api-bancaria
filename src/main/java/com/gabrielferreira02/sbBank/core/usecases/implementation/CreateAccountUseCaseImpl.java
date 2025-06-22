package com.gabrielferreira02.sbBank.core.usecases.implementation;

import com.gabrielferreira02.sbBank.core.domain.Account;
import com.gabrielferreira02.sbBank.core.domain.User;
import com.gabrielferreira02.sbBank.core.gateways.AccountGateway;
import com.gabrielferreira02.sbBank.core.usecases.interfaces.CreateAccountUseCase;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CreateAccountUseCaseImpl implements CreateAccountUseCase {

    private final AccountGateway accountGateway;

    public CreateAccountUseCaseImpl(AccountGateway accountGateway) {
        this.accountGateway = accountGateway;
    }

    @Override
    public Account execute(User user) {
        if(user == null) throw new IllegalArgumentException("Usuário inexistente");

        Account newAccount = new Account(
                null,
                user,
                accountGateway.generateAccountNumber(),
                new BigDecimal(0),
                new BigDecimal(15000),
                LocalDateTime.now()
        );

        return accountGateway.createAccount(newAccount);
    }
}
