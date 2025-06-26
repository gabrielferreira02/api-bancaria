package com.gabrielferreira02.sbBank.core.usecases.interfaces;

import com.gabrielferreira02.sbBank.core.domain.User;
import com.gabrielferreira02.sbBank.core.dto.CreateCustomerResponse;

public interface CreateCustomerUseCase {
    CreateCustomerResponse execute(User user, int paymentDay);
}
