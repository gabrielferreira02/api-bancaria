package com.gabrielferreira02.sbBank.infra.gateways;

import com.gabrielferreira02.sbBank.core.domain.Account;
import com.gabrielferreira02.sbBank.core.domain.BankingConstants;
import com.gabrielferreira02.sbBank.core.gateways.AccountGateway;

import java.util.UUID;

public class AccountGatewayImpl implements AccountGateway {

    @Override
    public String generateAccountNumber() {
        String code = BankingConstants.bankingCode;

        String id = UUID.randomUUID().toString()
                .replace("-", "")
                .substring(0,10)
                .toUpperCase();


        char digit = generateVerifierDigit(id);

        return code + " " + id + " " + digit;
    }

    private static char generateVerifierDigit(String id) {
        int sum = 0;

        for(int i = 0; i < id.length(); i++) {
            char c = id.charAt(i);
            int value = (c >= 'A' && c <= 'Z') ? (c - 'A' + 10) : (c - '0');
            sum += value;
        }

        int rest = sum % 36;
        return (rest < 10) ? (char) (rest + '0') : (char) (rest - 10 + 'A');
    }

    @Override
    public Account createAccount(Account account) {
        return null;
    }
}
