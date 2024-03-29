package br.com.intelligym.usecase.trainingprotocol;

import br.com.intelligym.model.enums.TrainingType;
import br.com.intelligym.model.enums.Variant;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public interface CreateTrainingProtocol {
    public record InputPort(@NotBlank @NotNull UUID customerId, UUID userId, @NotBlank @NotNull TrainingType trainingType, @NotBlank @NotNull Variant variant) {
    }

    sealed interface OutputPort permits OutputPort.Ok, OutputPort.Error {
        public static final record Ok(UUID id) implements OutputPort { }
        public static final record Error(String message) implements OutputPort { }
    }

    OutputPort execute(InputPort input);
}
