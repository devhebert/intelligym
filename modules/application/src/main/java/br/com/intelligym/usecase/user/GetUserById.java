package br.com.intelligym.usecase.user;

import br.com.intelligym.dto.user.UserDTO;

import java.util.UUID;

public interface GetUserById {
    public record InputPort(UUID id) {}

    public sealed interface OutputPort {
        public final record Ok(UserDTO user) implements OutputPort {}
        public final record Error(String message) implements OutputPort {}
    }

    public OutputPort execute(InputPort input);
}
