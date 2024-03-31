package br.com.intelligym.usecase.payment;

import br.com.intelligym.dto.paymentsolver.PaymentDTO;

import java.util.UUID;

public interface GetPaymentById {
    public record InputPort(UUID id) {}

    public sealed interface OutputPort permits OutputPort.Error, OutputPort.NotFound, OutputPort.Ok {
        public final record Ok(PaymentDTO payment) implements OutputPort {}
        public final record NotFound(String message) implements OutputPort {}
        public final record Error(String message) implements OutputPort {}
    }

    public OutputPort execute(InputPort input);
}
