package com.gabrielferreira02.sbBank.core.usecases.interfaces;

import com.gabrielferreira02.sbBank.core.domain.Account;
import com.gabrielferreira02.sbBank.core.domain.Card;

public interface CreateCardUseCase {
    Card execute(Account account, int paymentDay);
}
