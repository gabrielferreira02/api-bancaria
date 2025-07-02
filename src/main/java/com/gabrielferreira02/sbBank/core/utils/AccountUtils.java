package com.gabrielferreira02.sbBank.core.utils;

public class AccountUtils {
    public static char generateVerifierDigit(String id) {
        int sum = 0;

        for(int i = 0; i < id.length(); i++) {
            char c = id.charAt(i);
            int value = (c >= 'A' && c <= 'Z') ? (c - 'A' + 10) : (c - '0');
            sum += value;
        }

        int rest = sum % 36;
        return (rest < 10) ? (char) (rest + '0') : (char) (rest - 10 + 'A');
    }
}
