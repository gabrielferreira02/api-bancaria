package com.gabrielferreira02.sbBank.core.usecases.implementation;

import com.gabrielferreira02.sbBank.core.domain.User;
import com.gabrielferreira02.sbBank.core.exceptions.InvalidCepException;
import com.gabrielferreira02.sbBank.core.exceptions.InvalidCpfException;
import com.gabrielferreira02.sbBank.core.exceptions.UserAlreadyExistsException;
import com.gabrielferreira02.sbBank.core.gateways.UserGateway;
import com.gabrielferreira02.sbBank.core.usecases.interfaces.CreateUserUseCase;

import java.time.LocalDateTime;

public class CreateUserUseCaseImpl implements CreateUserUseCase {

    private final UserGateway userGateway;

    public CreateUserUseCaseImpl(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    @Override
    public User execute(User user) {
        validateUser(user);

        User newUser = new User(
                null,
                user.name(),
                user.cpf(),
                user.email(),
                user.password(),
                false,
                user.cep(),
                user.cep(),
                user.phone(),
                LocalDateTime.now()
        );

        return userGateway.createUser(newUser);
    }

    private void validateUser(User user) {
        if(user.email().isEmpty()) throw new IllegalArgumentException("O campo email não pode estar vazio");
        if(user.name().isEmpty()) throw new IllegalArgumentException("O campo nome não pode estar vazio");
        if(user.cep().isEmpty()) throw new IllegalArgumentException("O campo cep não pode estar vazio");
        if(user.cepComplement().isEmpty()) throw new IllegalArgumentException("Cep precisa de complemento");
        if(user.phone().isEmpty() || user.phone().length() < 8) throw new IllegalArgumentException("Telefone vazio ou inválido");
        if(user.password().length() < 8) throw new IllegalArgumentException("A senha precisa ter ao menos 8 caracteres");
        if(!userGateway.validateCep(user.cep())) throw new InvalidCepException("Cep inválido");
        if(!userGateway.validateCpf(user.cpf())) throw new InvalidCpfException("Cpf inválido");
        if(userGateway.findByCpf(user.cpf()) != null) throw new UserAlreadyExistsException("Cpf já cadastrado");
    }
}
