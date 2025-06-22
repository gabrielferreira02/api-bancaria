package com.gabrielferreira02.sbBank.core.usecases.implementation;

import com.gabrielferreira02.sbBank.core.domain.User;
import com.gabrielferreira02.sbBank.core.exceptions.EmailAlreadyRegisteredException;
import com.gabrielferreira02.sbBank.core.exceptions.InvalidCepException;
import com.gabrielferreira02.sbBank.core.exceptions.InvalidCpfException;
import com.gabrielferreira02.sbBank.core.exceptions.UserAlreadyExistsException;
import com.gabrielferreira02.sbBank.core.gateways.UserGateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseImplTest {

    @Mock
    private UserGateway userGateway;
    @InjectMocks
    private CreateUserUseCaseImpl createUserUseCase;

    @Test
    @DisplayName("Usuario criado com sucesso")
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

        User userOutput = new User(
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

        doReturn(true).when(userGateway).validateCpf(userInput.cpf());
        doReturn(true).when(userGateway).validateCep(userInput.cep());
        doReturn(null).when(userGateway).findByCpf(userInput.cpf());
        doReturn(null).when(userGateway).findByEmail(userInput.email());
        doReturn(userOutput).when(userGateway).createUser(any(User.class));

        User response = createUserUseCase.execute(userInput);

        assertNotNull(response);
        assertNotNull(response.id());
        assertEquals(userInput.name(), response.name());
        assertEquals(userInput.cpf(), response.cpf());
        assertEquals(userInput.email(), response.email());
        assertEquals(userInput.password(), response.password());
        assertFalse(response.verified());
        assertEquals(userInput.cep(), response.cep());
        assertEquals(userInput.cepComplement(), response.cepComplement());
        assertEquals(userInput.phone(), response.phone());

    }

    @Test
    @DisplayName("Falha ao criar usuario devido a cpf invalido")
    void error1() {
        User userInput = new User(
                null,
                "User",
                "11111111111",
                "user@email.com",
                "12345678",
                false,
                "22660-320",
                "131 casa A",
                "5521912345678",
                null
        );

        doReturn(true).when(userGateway).validateCep(userInput.cep());
        doReturn(false).when(userGateway).validateCpf(userInput.cpf());

        assertThrows(InvalidCpfException.class, () -> {
           createUserUseCase.execute(userInput);
        });
    }

    @Test
    @DisplayName("Falha ao criar usuario devido a cep invalido")
    void error2() {
        User userInput = new User(
                null,
                "User",
                "17824829707",
                "user@email.com",
                "12345678",
                false,
                "1111-111",
                "131 casa A",
                "5521912345678",
                null
        );

        doReturn(false).when(userGateway).validateCep(userInput.cep());

        assertThrows(InvalidCepException.class, () -> {
            createUserUseCase.execute(userInput);
        });
    }

    @Test
    @DisplayName("Falha ao criar usuario devido a 'name' invalido")
    void error3() {
        User userInput = new User(
                null,
                "",
                "17824829707",
                "user@email.com",
                "12345678",
                false,
                "22660-320",
                "131 casa A",
                "5521912345678",
                null
        );

        assertThrows(IllegalArgumentException.class, () -> {
            createUserUseCase.execute(userInput);
        });
    }

    @Test
    @DisplayName("Falha ao criar usuario devido a 'email' invalido")
    void error4() {
        User userInput = new User(
                null,
                "User",
                "17824829707",
                "",
                "12345678",
                false,
                "22660-320",
                "131 casa A",
                "5521912345678",
                null
        );

        assertThrows(IllegalArgumentException.class, () -> {
            createUserUseCase.execute(userInput);
        });
    }

    @Test
    @DisplayName("Falha ao criar usuario devido a campo cep em branco")
    void error5() {
        User userInput = new User(
                null,
                "User",
                "17824829707",
                "user@email.com",
                "12345678",
                false,
                "",
                "131 casa A",
                "5521912345678",
                null
        );

        assertThrows(IllegalArgumentException.class, () -> {
            createUserUseCase.execute(userInput);
        });
    }

    @Test
    @DisplayName("Falha ao criar usuario devido a telefone em branco")
    void error6() {
        User userInput = new User(
                null,
                "User",
                "17824829707",
                "user@email.com",
                "12345678",
                false,
                "22660-320",
                "131 casa A",
                "",
                null
        );

        assertThrows(IllegalArgumentException.class, () -> {
            createUserUseCase.execute(userInput);
        });
    }

    @Test
    @DisplayName("Falha ao criar usuario devido a complemento do cep em branco")
    void error7() {
        User userInput = new User(
                null,
                "User",
                "17824829707",
                "user@email.com",
                "12345678",
                false,
                "22660-320",
                "",
                "5521912345678",
                null
        );

        assertThrows(IllegalArgumentException.class, () -> {
            createUserUseCase.execute(userInput);
        });
    }

    @Test
    @DisplayName("Falha ao criar usuario devido a telefone com menos de 8 caracteres")
    void error8() {
        User userInput = new User(
                null,
                "User",
                "17824829707",
                "user@email.com",
                "12345678",
                false,
                "22660-320",
                "131 casa A",
                "7602767",
                null
        );

        assertThrows(IllegalArgumentException.class, () -> {
            createUserUseCase.execute(userInput);
        });
    }

    @Test
    @DisplayName("Falha ao criar usuario devido a senha curta demais")
    void error9() {
        User userInput = new User(
                null,
                "User",
                "17824829707",
                "user@email.com",
                "12345",
                false,
                "22660-320",
                "131 casa A",
                "5521912345678",
                null
        );

        assertThrows(IllegalArgumentException.class, () -> {
            createUserUseCase.execute(userInput);
        });
    }

    @Test
    @DisplayName("Falha ao criar usuario devido a cpf já cadastrado")
    void error10() {
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

        doReturn(true).when(userGateway).validateCpf(userInput.cpf());
        doReturn(true).when(userGateway).validateCep(userInput.cep());
        doReturn(userInput).when(userGateway).findByCpf(userInput.cpf());
        assertThrows(UserAlreadyExistsException.class, () -> {
            createUserUseCase.execute(userInput);
        });
    }

    @Test
    @DisplayName("Falha ao criar usuario devido a email já cadastrado")
    void error11() {
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

        doReturn(true).when(userGateway).validateCpf(userInput.cpf());
        doReturn(true).when(userGateway).validateCep(userInput.cep());
        doReturn(null).when(userGateway).findByCpf(userInput.cpf());
        doReturn(userInput).when(userGateway).findByEmail(userInput.email());
        assertThrows(EmailAlreadyRegisteredException.class, () -> {
            createUserUseCase.execute(userInput);
        });
    }

}