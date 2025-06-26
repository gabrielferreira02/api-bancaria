package com.gabrielferreira02.sbBank.core.usecases.implementation;

import com.gabrielferreira02.sbBank.core.domain.Account;
import com.gabrielferreira02.sbBank.core.domain.Card;
import com.gabrielferreira02.sbBank.core.domain.User;
import com.gabrielferreira02.sbBank.core.dto.CreateCustomerResponse;
import com.gabrielferreira02.sbBank.core.dto.SendEmailQueuePayload;
import com.gabrielferreira02.sbBank.core.gateways.QueueGateway;
import com.gabrielferreira02.sbBank.core.usecases.interfaces.CreateAccountUseCase;
import com.gabrielferreira02.sbBank.core.usecases.interfaces.CreateCardUseCase;
import com.gabrielferreira02.sbBank.core.usecases.interfaces.CreateUserUseCase;
import com.gabrielferreira02.sbBank.core.usecases.interfaces.CreateCustomerUseCase;

public class CreateCustomerUseCaseImpl implements CreateCustomerUseCase {

    private final CreateUserUseCase createUserUseCase;
    private final CreateAccountUseCase createAccountUseCase;
    private final CreateCardUseCase createCardUseCase;
    private final QueueGateway queueGateway;

    public CreateCustomerUseCaseImpl(CreateUserUseCase createUserUseCase,
                                     CreateAccountUseCase createAccountUseCase,
                                     CreateCardUseCase createCardUseCase,
                                     QueueGateway queueGateway) {
        this.createUserUseCase = createUserUseCase;
        this.createAccountUseCase = createAccountUseCase;
        this.createCardUseCase = createCardUseCase;
        this.queueGateway = queueGateway;
    }

    @Override
    public CreateCustomerResponse execute(User user, int paymentDay) {
        User newUser = createUserUseCase.execute(user);
        Account newAccount = createAccountUseCase.execute(newUser);
        Card newCard = createCardUseCase.execute(newAccount, paymentDay);

        SendEmailQueuePayload payload = new SendEmailQueuePayload(newUser.id(), newUser.name(), newUser.email());
        queueGateway.sendEmailQueue(payload);
        return new CreateCustomerResponse(
                newUser.name(),
                newUser.email(),
                newUser.cep(),
                newUser.phone(),
                newAccount.accountNumber(),
                newCard.number()
        );
    }
}
