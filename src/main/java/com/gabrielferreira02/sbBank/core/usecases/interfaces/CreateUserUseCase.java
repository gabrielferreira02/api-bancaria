package com.gabrielferreira02.sbBank.core.usecases.interfaces;

import com.gabrielferreira02.sbBank.core.domain.User;

public interface CreateUserUseCase {
    User execute(User user);
}
