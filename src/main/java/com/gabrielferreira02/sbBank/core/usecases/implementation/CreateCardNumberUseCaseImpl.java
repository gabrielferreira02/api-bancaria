package com.gabrielferreira02.sbBank.core.usecases.implementation;

import com.gabrielferreira02.sbBank.core.domain.BankingConstants;
import com.gabrielferreira02.sbBank.core.usecases.interfaces.CreateCardNumberUseCase;
import com.gabrielferreira02.sbBank.core.utils.CardUtils;

import java.util.Random;

public class CreateCardNumberUseCaseImpl implements CreateCardNumberUseCase {
    @Override
    public String execute() {
        String bin = BankingConstants.BIN;
        String digits = generateRandomDigits(BankingConstants.CARD_LENGTH - bin.length() - 1);
        String cardNumber = bin + digits;

        int luhnDigit = CardUtils.generateLuhnDigit(cardNumber);

        return cardNumber + luhnDigit;
    }

    private static String generateRandomDigits(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
