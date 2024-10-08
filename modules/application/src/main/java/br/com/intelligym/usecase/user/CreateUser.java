package br.com.intelligym.usecase.user;

import br.com.intelligym.model.enums.JobTitle;
import br.com.intelligym.model.enums.Roles;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public interface CreateUser {
    public record InputPort(@NotBlank @NotNull String username, @NotNull @NotBlank String password, @NotNull Roles role, @NotNull JobTitle jobTitle){}

    sealed interface OutputPort permits OutputPort.Ok, OutputPort.Error {
        public final record Ok(UUID id) implements OutputPort {}
        public final record Error(String message) implements OutputPort {}
    }

    OutputPort execute(InputPort inputPort);
}
