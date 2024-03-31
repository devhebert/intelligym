package br.com.intelligym.endpoint.payment;

import br.com.intelligym.endpoint.BasePaymentEndpoint;
import br.com.intelligym.usecase.payment.GetPaymentById;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class GetPaymentByIdEndpoint extends BasePaymentEndpoint {
    private final GetPaymentById useCase;

    public GetPaymentByIdEndpoint(GetPaymentById useCase) {
        this.useCase = useCase;
    }

    @Operation(summary = "Get payment by id")
    @GetMapping("/{id}")
    public ResponseEntity<?> execute(@PathVariable UUID id) {
        GetPaymentById.OutputPort result = useCase.execute(new GetPaymentById.InputPort(id));

        return switch (result) {
            case GetPaymentById.OutputPort.Ok ok -> ResponseEntity.ok(ok);
            case GetPaymentById.OutputPort.NotFound notFound -> ResponseEntity.notFound().build();
            case GetPaymentById.OutputPort.Error error -> ResponseEntity.badRequest().body(error.message());
        };
    }
}
