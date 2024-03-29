package br.com.intelligym.endpoint.trainingprotocol;

import br.com.intelligym.endpoint.BaseTrainingProtocolEndpoint;
import br.com.intelligym.model.enums.TrainingType;
import br.com.intelligym.model.enums.Variant;
import br.com.intelligym.usecase.trainingprotocol.CreateTrainingProtocol;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class CreateTrainingProtocolEndpoint extends BaseTrainingProtocolEndpoint {
    private final CreateTrainingProtocol useCase;

    public CreateTrainingProtocolEndpoint(CreateTrainingProtocol useCase) {
        this.useCase = useCase;
    }

    public record Request(UUID customerId, UUID userId, TrainingType trainingType, Variant variant) {
    }

    @Operation(summary = "Create a training protocol")
    @PostMapping("/create")
    public ResponseEntity<?> execute(@RequestBody @Valid Request request) {
        CreateTrainingProtocol.OutputPort result = this.useCase.execute(new CreateTrainingProtocol.InputPort(request.customerId(), request.userId(), request.trainingType(), request.variant()));

        return switch (result) {
            case CreateTrainingProtocol.OutputPort.Ok ok -> ResponseEntity.ok(ok);
            case CreateTrainingProtocol.OutputPort.Error error -> ResponseEntity.badRequest().body(error.message());
        };
    }

}
