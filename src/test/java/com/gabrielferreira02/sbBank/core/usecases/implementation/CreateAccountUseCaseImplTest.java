package com.gabrielferreira02.sbBank.core.usecases.implementation;

import com.gabrielferreira02.sbBank.core.domain.Account;
import com.gabrielferreira02.sbBank.core.domain.User;
import com.gabrielferreira02.sbBank.core.gateways.AccountGateway;
import com.gabrielferreira02.sbBank.core.gateways.UserGateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class CreateAccountUseCaseImplTest {

    @Mock
    private AccountGateway accountGateway;
    @Mock
    private CreateAccountNumberUseCaseImpl createAccountNumberUseCase;
    @InjectMocks
    private CreateAccountUseCaseImpl createAccountUseCase;

    @Test
    @DisplayName("Conta criada com sucesso")
    void success() {
        User userInput = new User(
                UUID.randomUUID(),
                "User",
                "17824829707",
                "user@email.com",
                "12345678",
                false,
                "22660-320",
                "131 casa A",
                "5521912345678",
                LocalDateTime.now()
        );

        Account account = new Account(
                null,
                userInput,
                "12345678910",
                new BigDecimal(0),
                new BigDecimal(15000),
                null
        );

        doReturn(account).when(accountGateway).createAccount(any(Account.class));
        doReturn("12345678910").when(createAccountNumberUseCase).execute();

        Account response = createAccountUseCase.execute(userInput);

        assertNotNull(response);
        assertEquals(userInput.name(), response.user().name());
        assertEquals(userInput.cpf(), response.user().cpf());
        assertEquals(userInput.email(), response.user().email());
        assertEquals(new BigDecimal(0), response.balance());
        assertEquals(new BigDecimal(15000), response.dailyTransferLimit());
        assertEquals("12345678910", response.accountNumber());

    }

    @Test
    @DisplayName("Falha ao criar conta por usuario inexistente")
    void error1() {
        assertThrows(IllegalArgumentException.class, () -> {
            createAccountUseCase.execute(null);
        });
    }

}