package br.com.intelligym.usecase.authentication;

import br.com.intelligym.model.enums.Roles;

import java.util.UUID;

public interface AuthenticationGenerete {
    public record InputPort(String username, String password) {}

    sealed interface OutputPort permits OutputPort.Ok, OutputPort.Error {
        public final record Ok(String token, UUID id, String username, Roles role) implements OutputPort {}
        public final record Error(String message) implements OutputPort {}
    }

    OutputPort execute(InputPort inputPort);
}
