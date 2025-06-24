package com.gabrielferreira02.sbBank.core.usecases.implementation;

import com.gabrielferreira02.sbBank.core.domain.Account;
import com.gabrielferreira02.sbBank.core.domain.BankingConstants;
import com.gabrielferreira02.sbBank.core.domain.Card;
import com.gabrielferreira02.sbBank.core.domain.User;
import com.gabrielferreira02.sbBank.core.exceptions.InvalidInvoicePaymentDayException;
import com.gabrielferreira02.sbBank.core.gateways.CardGateway;
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
class CreateCardUseCaseImplTest {

    @Mock
    private CardGateway cardGateway;
    @InjectMocks
    private CreateCardUseCaseImpl createCardUseCase;

    @Test
    @DisplayName("Cria o cartão de crédito com sucesso")
    void success() {
        Account account = new Account(
                UUID.randomUUID(),
                new User(UUID.randomUUID(),
                        "User",
                        "12345678910",
                        "user@email.com",
                        "12345678",
                        true,
                        "11111-111",
                        "casa A",
                        "21999999999",
                        LocalDateTime.now()),
                "123456789",
                new BigDecimal(0),
                new BigDecimal(15000),
                LocalDateTime.now()
        );

        int paymentDay = BankingConstants.DAYS_ALLOWED_FOR_INVOICE_PAYMENT.getFirst();

        Card card = new Card(
                UUID.randomUUID(),
                "123456789",
                account,
                new BigDecimal(15000),
                LocalDateTime.now(),
                LocalDateTime.now(),
                paymentDay,
                false
        );

        doReturn("123456789").when(cardGateway).generateNumber();
        doReturn(card).when(cardGateway).createCard(any(Card.class));

        Card response = createCardUseCase.execute(account, paymentDay);

        assertNotNull(response);
        assertEquals("123456789", response.number());
        assertEquals("User", response.account().user().name());
        assertEquals("123456789", response.account().accountNumber());
        assertFalse(response.active());
        assertEquals(paymentDay, response.paymentDay());
    }

    @Test
    @DisplayName("Falha ao criar cartão por data de pagamento invalida")
    void error1() {
        Account account = new Account(
                UUID.randomUUID(),
                new User(UUID.randomUUID(),
                        "User",
                        "12345678910",
                        "user@email.com",
                        "12345678",
                        true,
                        "11111-111",
                        "casa A",
                        "21999999999",
                        LocalDateTime.now()),
                "123456789",
                new BigDecimal(0),
                new BigDecimal(15000),
                LocalDateTime.now()
        );

        int paymentDay = 1;

        assertThrows(InvalidInvoicePaymentDayException.class, () -> {
            createCardUseCase.execute(account, paymentDay);
        });
    }

    @Test
    @DisplayName("Falha ao criar cartão por conta invalida")
    void error2() {
        int paymentDay = BankingConstants.DAYS_ALLOWED_FOR_INVOICE_PAYMENT.getFirst();

        assertThrows(IllegalArgumentException.class, () -> {
            createCardUseCase.execute(null, paymentDay);
        });
    }
}