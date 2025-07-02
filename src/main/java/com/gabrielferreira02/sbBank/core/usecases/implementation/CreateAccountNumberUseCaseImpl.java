package com.gabrielferreira02.sbBank.core.usecases.implementation;

import com.gabrielferreira02.sbBank.core.domain.BankingConstants;
import com.gabrielferreira02.sbBank.core.usecases.interfaces.CreateAccountNumberUseCase;
import com.gabrielferreira02.sbBank.core.utils.AccountUtils;

import java.util.UUID;

public class CreateAccountNumberUseCaseImpl implements CreateAccountNumberUseCase {
    @Override
    public String execute() {
        String code = BankingConstants.BANKING_CODE;

        String id = UUID.randomUUID().toString()
                .replace("-", "")
                .substring(0,10)
                .toUpperCase();


        char digit = AccountUtils.generateVerifierDigit(id);

        return code + id + digit;
    }
}
