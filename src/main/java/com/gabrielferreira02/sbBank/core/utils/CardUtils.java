package com.gabrielferreira02.sbBank.core.utils;

public class CardUtils {
    public static int generateLuhnDigit(String cardNumber) {
        int sum = 0;
        boolean swap = false;

        for(int i = cardNumber.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(cardNumber.charAt(i));
            if(swap) {
                digit *= 2;
                if(digit > 9) {
                    digit = (digit % 10) + 1;
                }
            }
            sum += digit;
            swap = !swap;
        }
        return (10 - (sum % 10)) % 10;
    }
}
