package br.com.intelligym.usecase.payment;

import br.com.intelligym.model.enums.PaymentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public interface CreatePayment {
    public record InputPort(UUID customerId, UUID planId, PaymentType paymentType, BigDecimal value, String description) { }

    sealed interface OutputPort permits OutputPort.Ok, OutputPort.Error {
        public final record Ok(UUID id) implements OutputPort {};
        public final record Error(String message) implements OutputPort {};
    }

    OutputPort execute(InputPort input);
}
