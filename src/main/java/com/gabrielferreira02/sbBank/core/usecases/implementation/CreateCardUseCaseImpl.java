package com.gabrielferreira02.sbBank.core.usecases.implementation;

import com.gabrielferreira02.sbBank.core.domain.Account;
import com.gabrielferreira02.sbBank.core.domain.BankingConstants;
import com.gabrielferreira02.sbBank.core.domain.Card;
import com.gabrielferreira02.sbBank.core.exceptions.InvalidInvoicePaymentDayException;
import com.gabrielferreira02.sbBank.core.gateways.CardGateway;
import com.gabrielferreira02.sbBank.core.usecases.interfaces.CreateCardNumberUseCase;
import com.gabrielferreira02.sbBank.core.usecases.interfaces.CreateCardUseCase;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CreateCardUseCaseImpl implements CreateCardUseCase {

    private final CardGateway cardGateway;
    private final CreateCardNumberUseCase createCardNumberUseCase;

    public CreateCardUseCaseImpl(CardGateway cardGateway, CreateCardNumberUseCase createCardNumberUseCase) {
        this.cardGateway = cardGateway;
        this.createCardNumberUseCase = createCardNumberUseCase;
    }

    @Override
    public Card execute(Account account, int paymentDay) {
        if(account == null) throw new IllegalArgumentException("Conta inexistente");
        if(!BankingConstants.DAYS_ALLOWED_FOR_INVOICE_PAYMENT.contains(paymentDay)) throw new InvalidInvoicePaymentDayException("Dia de pagamento da fatura inválido");

        Card newCard = new Card(
                null,
                createCardNumberUseCase.execute(),
                account,
                new BigDecimal(15000),
                LocalDateTime.now(),
                LocalDateTime.now(),
                paymentDay,
                false
        );

        return cardGateway.createCard(newCard);
    }
}
