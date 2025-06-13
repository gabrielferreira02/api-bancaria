package com.gabrielferreira02.sbBank.core.domain;

import java.math.BigDecimal;
import java.util.List;

public final class BankingConstants {
    public static final double SAVINGS_ACCOUNT_INTEREST_RATE = 0.005;
    public static final List<Integer> DAYS_ALLOWED_FOR_INVOICE_PAYMENT = List.of(6, 12, 19, 26);
    public static final BigDecimal DAILY_TRANSFER_LIMIT = new BigDecimal(15000);

    public BankingConstants() {}
}
