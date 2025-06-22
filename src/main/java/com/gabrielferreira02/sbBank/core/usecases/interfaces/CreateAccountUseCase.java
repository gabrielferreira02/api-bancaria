package com.gabrielferreira02.sbBank.core.usecases.interfaces;

import com.gabrielferreira02.sbBank.core.domain.Account;
import com.gabrielferreira02.sbBank.core.domain.User;

public interface CreateAccountUseCase {
    Account execute(User user);
}
