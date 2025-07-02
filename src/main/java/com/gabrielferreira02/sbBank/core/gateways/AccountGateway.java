package com.gabrielferreira02.sbBank.core.gateways;

import com.gabrielferreira02.sbBank.core.domain.Account;

public interface AccountGateway {
    Account createAccount(Account account);
}
