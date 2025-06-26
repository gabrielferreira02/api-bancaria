package com.gabrielferreira02.sbBank.core.usecases.implementation;

import com.gabrielferreira02.sbBank.core.domain.Account;
import com.gabrielferreira02.sbBank.core.domain.BankingConstants;
import com.gabrielferreira02.sbBank.core.domain.Card;
import com.gabrielferreira02.sbBank.core.domain.User;
import com.gabrielferreira02.sbBank.core.dto.CreateCustomerResponse;
import com.gabrielferreira02.sbBank.core.dto.SendEmailQueuePayload;
import com.gabrielferreira02.sbBank.core.exceptions.InvalidInvoicePaymentDayException;
import com.gabrielferreira02.sbBank.core.gateways.QueueGateway;
import com.gabrielferreira02.sbBank.core.usecases.interfaces.CreateAccountUseCase;
import com.gabrielferreira02.sbBank.core.usecases.interfaces.CreateCardUseCase;
import com.gabrielferreira02.sbBank.core.usecases.interfaces.CreateUserUseCase;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateCustomerUseCaseImplTest {

    @Mock
    private CreateUserUseCase createUserUseCase;
    @Mock
    private CreateAccountUseCase createAccountUseCase;
    @Mock
    private CreateCardUseCase createCardUseCase;
    @Mock
    private QueueGateway queueGateway;
    @InjectMocks
    private CreateCustomerUseCaseImpl createCustomerUseCase;

    @Test
    @DisplayName("Cria um cliente com sucesso")
    void success() {
        User userInput = new User(
                null,
                "User",
                "17824829707",
                "user@email.com",
                "12345678",
                false,
                "22660-320",
                "131 casa A",
                "5521912345678",
                null
        );

        User mockUser = new User(
                UUID.randomUUID(),
                userInput.name(),
                userInput.cpf(),
                userInput.email(),
                userInput.password(),
                userInput.verified(),
                userInput.cep(),
                userInput.cepComplement(),
                userInput.phone(),
                LocalDateTime.now()
        );

        Account mockAccount = new Account(
                UUID.randomUUID(),
                mockUser,
                "12345678910",
                new BigDecimal(0),
                new BigDecimal(15000),
                LocalDateTime.now()
        );

        int paymentDay = BankingConstants.DAYS_ALLOWED_FOR_INVOICE_PAYMENT.getFirst();
        Card mockCard = new Card(
                UUID.randomUUID(),
                "123456789",
                mockAccount,
                new BigDecimal(15000),
                LocalDateTime.now(),
                LocalDateTime.now(),
                paymentDay,
                false
        );

        SendEmailQueuePayload payload = new SendEmailQueuePayload(
                mockUser.id(),
                mockUser.name(),
                mockUser.email()
        );

        doReturn(mockUser).when(createUserUseCase).execute(userInput);
        doReturn(mockAccount).when(createAccountUseCase).execute(mockUser);
        doReturn(mockCard).when(createCardUseCase).execute(mockAccount, paymentDay);

        CreateCustomerResponse response = createCustomerUseCase.execute(userInput, paymentDay);

        verify(createUserUseCase).execute(userInput);
        verify(createAccountUseCase).execute(mockUser);
        verify(createCardUseCase).execute(mockAccount, paymentDay);
        verify(queueGateway).sendEmailQueue(payload);

        assertNotNull(response);
        assertEquals(mockUser.name(), response.name());
        assertEquals(mockUser.email(), response.email());
        assertEquals(mockUser.phone(), response.phone());
        assertEquals(mockUser.cep(), response.cep());
        assertEquals(mockAccount.accountNumber(), response.accountNumber());
        assertEquals(mockCard.number(), response.cardNumber());
    }

    @Test
    @DisplayName("Falha ao criar cliente devido a algum dado errado de usuario")
    void error1() {
        User userInput = new User(
                null,
                "User",
                "17824829707",
                "user@email.com",
                "",
                false,
                "22660-320",
                "131 casa A",
                "5521912345678",
                null
        );

        int paymentDay = BankingConstants.DAYS_ALLOWED_FOR_INVOICE_PAYMENT.getFirst();

        doThrow(IllegalArgumentException.class).when(createUserUseCase).execute(userInput);

        assertThrows(IllegalArgumentException.class, () -> {
            createCustomerUseCase.execute(userInput, paymentDay);
        });

        verifyNoInteractions(createAccountUseCase, createCardUseCase, queueGateway);
    }

    @Test
    @DisplayName("Falha ao criar cliente devido a dia de pagamento invalido")
    void error2() {
        User userInput = new User(
                null,
                "User",
                "17824829707",
                "user@email.com",
                "12345678",
                false,
                "22660-320",
                "131 casa A",
                "5521912345678",
                null
        );

        User mockUser = new User(
                UUID.randomUUID(),
                userInput.name(),
                userInput.cpf(),
                userInput.email(),
                userInput.password(),
                userInput.verified(),
                userInput.cep(),
                userInput.cepComplement(),
                userInput.phone(),
                LocalDateTime.now()
        );

        Account mockAccount = new Account(
                UUID.randomUUID(),
                mockUser,
                "12345678910",
                new BigDecimal(0),
                new BigDecimal(15000),
                LocalDateTime.now()
        );

        int paymentDay = 1;

        doReturn(mockUser).when(createUserUseCase).execute(userInput);
        doReturn(mockAccount).when(createAccountUseCase).execute(mockUser);
        doThrow(InvalidInvoicePaymentDayException.class).when(createCardUseCase).execute(mockAccount, paymentDay);

        assertThrows(InvalidInvoicePaymentDayException.class, () -> {
            createCustomerUseCase.execute(userInput, paymentDay);
        });

        verify(createUserUseCase).execute(userInput);
        verify(createAccountUseCase).execute(mockUser);
        verify(createCardUseCase).execute(mockAccount, paymentDay);
        verifyNoInteractions(queueGateway);
    }

}