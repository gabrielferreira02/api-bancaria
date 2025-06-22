package com.gabrielferreira02.sbBank.core.gateways;

import com.gabrielferreira02.sbBank.core.domain.User;

public interface UserGateway {
    boolean validateCpf(String cpf);
    boolean validateCep(String cep);
    User findByCpf(String cpf);
    User createUser(User user);
    User findByEmail(String email);
}
