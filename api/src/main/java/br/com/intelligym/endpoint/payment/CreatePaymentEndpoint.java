package br.com.intelligym.endpoint.payment;

import br.com.intelligym.endpoint.BasePaymentEndpoint;
import br.com.intelligym.model.enums.PaymentType;
import br.com.intelligym.usecase.payment.CreatePayment;

import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
public class CreatePaymentEndpoint extends BasePaymentEndpoint {
    private final CreatePayment useCase;

    public CreatePaymentEndpoint(CreatePayment useCase) {
        this.useCase = useCase;
    }

    public record Request(UUID customerId, UUID planId, PaymentType paymentType, BigDecimal value, String description){}

    @Operation(summary = "Create a payment")
    @PostMapping(value = "create")
    public ResponseEntity<?> execute(@RequestBody @Valid Request request) {
        CreatePayment.OutputPort result = this.useCase.execute(new CreatePayment.InputPort(request.customerId(), request.planId(), request.paymentType(), request.value(), request.description()));

        return switch (result) {
            case CreatePayment.OutputPort.Ok ok -> ResponseEntity.ok(ok);
            case CreatePayment.OutputPort.Error error -> ResponseEntity.badRequest().body(error.message());
        };
    }
}
